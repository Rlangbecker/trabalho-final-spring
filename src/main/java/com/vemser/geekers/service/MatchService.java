package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.MatchEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;
    private final DesafioService desafioService;

    private final UsuarioService usuarioService;
    private final UsuarioLoginService usuarioLoginService;

    public MatchDTO create(Integer id, MatchCreateDTO matchCreateDTO) throws RegraDeNegocioException {
        return null;
    }

    public List<MatchDTO> list() throws RegraDeNegocioException {

        return matchRepository.findAll()
                .stream()
                .map(matchEntity -> objectMapper.convertValue(matchEntity, MatchDTO.class))
                .toList();

    }

    public List<MatchDTO> listByUser(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(id);
        return matchRepository.findMatchEntitiesByUsuario(usuarioEntity)
                .stream()
                .map(matchEntity -> objectMapper.convertValue(matchEntity, MatchDTO.class))
                .toList();

    }

    public MatchDTO findById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(matchRepository.findById(id), MatchDTO.class);
    }

    public MatchDTO resolverDesafio(MatchCreateDTO matchCreateDTO, Integer resposta) throws RegraDeNegocioException {
        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
        DesafioDTO desafio = desafioService.findByUsuario(matchCreateDTO.getIdUsuario());
        if (resposta == desafio.getResposta()) {
            MatchEntity matchEntity = objectMapper.convertValue(matchCreateDTO, MatchEntity.class);
            matchEntity.setIdUsuario(matchCreateDTO.getIdUsuario());
            UsuarioEntity usuario = usuarioService.findById(matchEntity.getIdUsuario());
            matchEntity.setUsuario(usuario);
            matchEntity.setUsuarioMain(usuarioService.findById(login.getIdUsuario()).getIdUsuario());
            matchEntity.setAtivo(TipoAtivo.ATIVO);
            MatchEntity matchCriado = matchRepository.save(matchEntity);
            MatchDTO matchDTO = objectMapper.convertValue(matchCriado, MatchDTO.class);
            matchDTO.setIdUsuario(matchCriado.getIdUsuario());
            matchDTO.setIdUsuarioMatch(matchCriado.getUsuarioMain());
            return matchDTO;
        } else {
            throw new RegraDeNegocioException("Resposta errada!");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        MatchDTO matchDTO = findById(id);
        matchDTO.setAtivo(TipoAtivo.INATIVO);
        MatchEntity match = objectMapper.convertValue(matchDTO, MatchEntity.class);
        matchRepository.save(match);
    }

    public PageDTO<MatchDTO> listMatchPaginada(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<MatchEntity> paginaDoRepositorio = matchRepository.findAll(pageRequest);
        List<MatchDTO> matchDTOS = paginaDoRepositorio.getContent().stream()
                .map(matchs -> objectMapper.convertValue(matchs, MatchDTO.class))
                .toList();
        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                matchDTOS
        );

    }

//    public UsuarioMatchDadosDTO listaComNomeUsuarioMatch(Integer idUsuario) throws RegraDeNegocioException {
//        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
//        UsuarioEntity usuario1 = usuarioService.findById(idUsuario);
//        UsuarioEntity usuarioLogado = usuarioService.findById(login.getIdUsuario());
//        UsuarioMatchDadosDTO usuarioMatchDadosDTO = null;
//        usuarioMatchDadosDTO.setNomeUsuario(usuario1.getNome());
//        usuarioMatchDadosDTO.setIdUsuario(usuario1.getIdUsuario());
//        usuarioMatchDadosDTO.setIdUsuarioLogado(usuarioLogado.getIdUsuario());
//        usuarioMatchDadosDTO.setNomeUsuarioLogado(usuarioLogado.getNome());
//        return usuarioMatchDadosDTO;
//    }
}
