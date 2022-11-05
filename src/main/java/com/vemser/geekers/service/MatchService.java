package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.dto.MatchCreateDTO;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.dto.PageDTO;
import com.vemser.geekers.entity.MatchEntity;
import com.vemser.geekers.entity.UsuarioEntity;
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
    private final EmailService emailService;

    private final UsuarioService usuarioService;

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

    public MatchDTO resolverDesafio(MatchCreateDTO matchCreateDTO, Integer resposta, Integer idUsuario) throws RegraDeNegocioException {
        DesafioDTO desafio = desafioService.findByUsuario(idUsuario);
        if (resposta == desafio.getResposta()) {
            MatchEntity matchEntity = objectMapper.convertValue(matchCreateDTO, MatchEntity.class);
            matchEntity.setIdUsuario(matchCreateDTO.getIdUsuario());
            UsuarioEntity usuario = usuarioService.findById(matchEntity.getIdUsuario());
            matchEntity.setUsuario(usuario);

            MatchEntity matchCriado = matchRepository.save(matchEntity);
            MatchDTO matchDTO = objectMapper.convertValue(matchCriado, MatchDTO.class);
            matchDTO.setIdUsuario(matchCriado.getIdUsuario());
            return matchDTO;
        } else {
            throw new RegraDeNegocioException("Resposta errada!");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        MatchDTO matchDTO = findById(id);
        MatchEntity matchEntity = objectMapper.convertValue(matchDTO, MatchEntity.class);
        matchRepository.delete(matchEntity);
    }

    public PageDTO<MatchDTO> listMatchPaginada(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<MatchEntity> paginaDoRepositorio = matchRepository.findAll(pageRequest);
        List<MatchDTO> pessoasDaPagina = paginaDoRepositorio.getContent().stream()
                .map(pessoaEntity -> objectMapper.convertValue(pessoaEntity, MatchDTO.class))
                .toList();
        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                pessoasDaPagina
        );

    }
}
