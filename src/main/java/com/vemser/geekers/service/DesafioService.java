package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.DesafioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    public DesafioDTO create(Integer idUsuario, DesafioCreateDTO desafioCreateDTO) throws RegraDeNegocioException {
            UsuarioEntity usuario = usuarioService.findById(idUsuario);
            DesafioEntity desafio = objectMapper.convertValue(desafioCreateDTO, DesafioEntity.class);
            desafio.setUsuario(usuario);
            desafio.getUsuario().setIdUsuario(idUsuario);

            desafioRepository.save(desafio);

            DesafioDTO desafioDTO = objectMapper.convertValue(desafio, DesafioDTO.class);
            desafioDTO.setIdUsuario(idUsuario);
            return desafioDTO;

    }

    public List<DesafioDTO> list(){
        return desafioRepository.findAll()
                .stream()
                .map(desafio -> objectMapper.convertValue(desafio, DesafioDTO.class))
                .toList();
    }

    public DesafioDTO findByUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(id);
        DesafioEntity desafioEntity = desafioRepository.findDesafioEntityByUsuario(usuarioEntity);
        DesafioDTO desafioDTO = objectMapper.convertValue(desafioEntity, DesafioDTO.class);
        return desafioDTO;
    }

    public DesafioDTO findById(Integer id) throws RegraDeNegocioException {
        DesafioEntity desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Desafio não encontrado!"));
        DesafioDTO desafioDTO = objectMapper.convertValue(desafio, DesafioDTO.class);
        desafioDTO.setIdUsuario(desafio.getUsuario().getIdUsuario());
        return desafioDTO;
    }

    public DesafioDTO edit(Integer id, DesafioCreateDTO desafioCreateDTO) throws RegraDeNegocioException {
        DesafioDTO desafio = findById(id);
        DesafioEntity desafioEntity = objectMapper.convertValue(desafio, DesafioEntity.class);
        desafioEntity.setPergunta(desafioCreateDTO.getPergunta());
        desafioEntity.setResposta(desafioCreateDTO.getResposta());

        desafioRepository.save(desafioEntity);

        return objectMapper.convertValue(desafioEntity, DesafioDTO.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException{
        DesafioDTO desafio = findById(id);
        DesafioEntity desafioEntity = objectMapper.convertValue(desafio, DesafioEntity.class);
        desafioRepository.delete(desafioEntity);
    }


}
