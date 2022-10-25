package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.DesafioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesafioService {

    private DesafioRepository desafioRepository;
    private ObjectMapper objectMapper;

    public DesafioService(DesafioRepository desafioRepository, ObjectMapper objectMapper){
        this.desafioRepository = desafioRepository;
        this.objectMapper = objectMapper;
    }

    public DesafioDTO create(DesafioCreateDTO desafio, Integer id) {
        DesafioDTO desafioDTO;
        try {
            Desafio desafioEntity = objectMapper.convertValue(desafio, Desafio.class);
            Desafio desafioCriado = desafioRepository.adicionar(desafioEntity, id);
            desafioDTO = objectMapper.convertValue(desafioCriado, DesafioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
        return desafioDTO;
    }

    public List<DesafioDTO> list() throws BancoDeDadosException {
        return desafioRepository.listar()
                .stream()
                .map(desafio -> objectMapper.convertValue(desafio, DesafioDTO.class))
                .toList();
    }

    public DesafioDTO update(Integer id,
                           DesafioCreateDTO desafioAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        Desafio desafioEntity = objectMapper.convertValue(desafioAtualizar, Desafio.class);
        DesafioDTO desafioDTO = objectMapper.convertValue(desafioRepository.editar(id,desafioEntity), DesafioDTO.class);
        return desafioDTO;
    }

    public Desafio findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Desafio desafioEncontrado = desafioRepository.listar().stream()
                .filter(desafio -> desafio.getIdDesafio().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Desafio não encontrado"));
        return desafioEncontrado;
    }


    public void delete(Integer id) throws Exception {
        Desafio desafioRetornado = findById(id);
        desafioRepository.remover(desafioRetornado.getIdDesafio());
    }

}
