package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.dto.MatchCreateDTO;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.Match;
import com.vemser.geekers.entity.Usuario;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;
    private final DesafioService desafioService;
    private final EmailService emailService;

    private final UsuarioService usuarioService;

    public MatchDTO create(MatchCreateDTO matchCreateDTO) {
        MatchDTO matchDTO;
        try {
            Match matchEntity = objectMapper.convertValue(matchCreateDTO, Match.class);
            usuarioService.findById(matchEntity.getUsuario());
            usuarioService.findById(matchEntity.getUsuarioMain());
            Match matchCriado = matchRepository.adicionarMatch(matchEntity);
            matchDTO = objectMapper.convertValue(matchCriado, MatchDTO.class);
            return matchDTO;
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MatchDTO> list() throws RegraDeNegocioException {
        try {
            return matchRepository.listar()
                    .stream()
                    .map(match -> objectMapper.convertValue(match, MatchDTO.class))
                    .toList();
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar matchs!");
        }
    }

    public List<MatchDTO> listByUser(Integer id) throws RegraDeNegocioException {
        try {
            return matchRepository.listarPorUsuario(id)
                    .stream()
                    .map(match -> objectMapper.convertValue(match, MatchDTO.class))
                    .toList();
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar matchs!");
        }

    }

    public Match findById(Integer id) throws RegraDeNegocioException {
        try {
            Match matchEncontrado = matchRepository.listar().stream()
                    .filter(match -> match.getIdMatch().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Match não encontrado"));
            return matchEncontrado;
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar matchs!");
        }
    }

    public MatchDTO resolverDesafio(MatchCreateDTO matchCreateDTO, Integer resposta) throws RegraDeNegocioException {
        try {
            DesafioDTO desafio = desafioService.listByUser(matchCreateDTO.getUsuario());
            if (resposta == desafio.getResposta()) {
                Match matchEntity = objectMapper.convertValue(matchCreateDTO, Match.class);
                Usuario usuario1 = usuarioService.findById(matchEntity.getUsuario());
                Usuario usuario2 = usuarioService.findById(matchEntity.getUsuarioMain());
                UsuarioDTO user1Dto = objectMapper.convertValue(usuario1, UsuarioDTO.class);
                UsuarioDTO user2Dto = objectMapper.convertValue(usuario2, UsuarioDTO.class);

                try {
                    emailService.sendEmail(user1Dto, user2Dto, TipoEmail.MATCH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Match matchCriado = matchRepository.adicionarMatch(matchEntity);
                MatchDTO matchDTO = objectMapper.convertValue(matchCriado, MatchDTO.class);
                return matchDTO;
            } else {
                throw new RegraDeNegocioException("Resposta errada!");
            }
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao resolver o desafio! ");
        }

    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            Match matchRetornado = findById(id);
            matchRepository.remover(matchRetornado.getIdMatch());
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar matchs!");
        }
    }
}
