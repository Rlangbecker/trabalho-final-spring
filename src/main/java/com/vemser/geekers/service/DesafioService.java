package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.BancoDeDadosException;
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

    public DesafioDTO create(DesafioCreateDTO desafio, Integer id) throws RegraDeNegocioException {
        DesafioDTO desafioDTO;
        try {
            Desafio desafioEntity = objectMapper.convertValue(desafio, Desafio.class);
            UsuarioEntity usuarioEntity = usuarioService.findById(id);
            if (listByUser(id) == null){
                Desafio desafioCriado = desafioRepository.adicionarDesafio(desafioEntity, usuarioEntity);
                desafioDTO = objectMapper.convertValue(desafioCriado, DesafioDTO.class);
                desafioDTO.setUsuarioEntity(usuarioEntity);
                return desafioDTO;
            }
            else {
                throw  new RegraDeNegocioException("Esse usuario já possui um desafio!");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar!");
        }
    }

    public List<DesafioDTO> list() throws RegraDeNegocioException {
        try {
            return desafioRepository.listar()
                    .stream()
                    .map(usuario -> objectMapper.convertValue(usuario, DesafioDTO.class))
                    .toList();
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar usuario!");
        }
    }

    public DesafioDTO update(Integer id,
                           DesafioCreateDTO desafioAtualizar) throws RegraDeNegocioException {
        try{
            findById(id);
            Desafio desafioEntity = objectMapper.convertValue(desafioAtualizar, Desafio.class);
            desafioRepository.editar(id,desafioEntity);
            return objectMapper.convertValue(desafioEntity, DesafioDTO.class);
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao atualizar!");
        }
    }

    public DesafioDTO listByUser(Integer id) throws RegraDeNegocioException {
        try {
            Desafio desafioEntity = objectMapper.convertValue(desafioRepository.listarByUser(id), Desafio.class);
            return objectMapper.convertValue(desafioEntity, DesafioDTO.class);
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar usuario!");
        }

    }

    public DesafioDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Desafio desafioEntity = objectMapper.convertValue(desafioRepository.listarById(id), Desafio.class);
            return objectMapper.convertValue(desafioEntity, DesafioDTO.class);
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar o desafio!");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            Desafio desafioRetornado = objectMapper.convertValue(desafioRepository.listarById(id), Desafio.class);
            desafioRepository.remover(desafioRetornado.getIdDesafio());
        }
        catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar!");
        }
    }

}
