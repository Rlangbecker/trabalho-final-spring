package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.dto.MatchCreateDTO;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.entity.Match;
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

    public List<MatchDTO> list() throws BancoDeDadosException {
        return matchRepository.listar()
                .stream()
                .map(match -> objectMapper.convertValue(match, MatchDTO.class))
                .toList();
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

    public Match findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Match matchEncontrado = matchRepository.listar().stream()
                .filter(match -> match.getIdMatch().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Match não encontrado"));
        return matchEncontrado;
    }

    public MatchDTO resolverDesafio(MatchCreateDTO matchCreateDTO, Integer resposta) throws BancoDeDadosException, RegraDeNegocioException {
        DesafioDTO desafio = desafioService.listByUser(matchCreateDTO.getUsuario());
        if(resposta == desafio.getResposta()){
            Match matchEntity = objectMapper.convertValue(matchCreateDTO, Match.class);
            usuarioService.findById(matchEntity.getUsuario());
            usuarioService.findById(matchEntity.getUsuarioMain());
            Match matchCriado = matchRepository.adicionarMatch(matchEntity);
            MatchDTO matchDTO = objectMapper.convertValue(matchCriado, MatchDTO.class);
            return matchDTO;
        }
        else {
            throw new RegraDeNegocioException("Resposta errada!");
        }
    }

    public void delete(Integer id) throws Exception {
        Match matchRetornado = findById(id);
        matchRepository.remover(matchRetornado.getIdMatch());
    }
}
