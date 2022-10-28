package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.dto.DesafioUsuarioDTO;
import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.entity.Usuario;
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
            Usuario usuario = usuarioService.findById(id);
            if (listByUser(id) == null){
                Desafio desafioCriado = desafioRepository.adicionarDesafio(desafioEntity, usuario);
                desafioDTO = objectMapper.convertValue(desafioCriado, DesafioDTO.class);
                desafioDTO.setUsuario(usuario);
                return desafioDTO;
            }
            else {
                throw  new RegraDeNegocioException("Esse usuario já possui um desafio!");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar!");
        }
    }

//    public List<DesafioDTO> list() throws Re {
//        try {
//            Desafio desafioEntity = objectMapper.convertValue(desafioRepository.listar(), Desafio.class);
//            return objectMapper.convertValue(desafioEntity, DesafioDTO.class);
//        }
//        catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao listar usuario!");
//        }
//    }


    //Erro no update
    public DesafioDTO update(Integer id,
                           DesafioCreateDTO desafioAtualizar) throws RegraDeNegocioException {
        try{
            Desafio desafioEntity = objectMapper.convertValue(desafioAtualizar, Desafio.class);
            Usuario usuario = findById(id).getUsuario();
            desafioEntity.setUsuario(usuario);
            return objectMapper.convertValue(desafioRepository.editar(id,desafioEntity), DesafioDTO.class);
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
