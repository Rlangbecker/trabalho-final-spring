package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.DesafioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    public DesafioDTO create(Integer id, DesafioCreateDTO desafioCreateDTO) throws RegraDeNegocioException {
        try {
            UsuarioEntity usuario = usuarioService.findById(id);
            DesafioEntity desafio = objectMapper.convertValue(desafioCreateDTO, DesafioEntity.class);
            desafio.setUsuario(usuario);

            DesafioDTO desafioDTO = objectMapper.convertValue(desafioRepository.save(desafio), DesafioDTO.class);
            desafioDTO.setIdUsuario(id);
            return desafioDTO;
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<DesafioDTO> list(){
        return desafioRepository.findAll()
                .stream()
                .map(desafio -> objectMapper.convertValue(desafio, DesafioDTO.class))
                .toList();
    }

    public DesafioDTO findById(Integer id) throws RegraDeNegocioException {
        DesafioEntity desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Desafio não encontrado!"));
        return objectMapper.convertValue(desafio, DesafioDTO.class);
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
