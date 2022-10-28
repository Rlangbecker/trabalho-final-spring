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

    public DesafioDTO create(DesafioCreateDTO desafio, Integer id) {
        DesafioDTO desafioDTO;
        try {
            Desafio desafioEntity = objectMapper.convertValue(desafio, Desafio.class);
            Usuario usuario = usuarioService.findById(id);
            Desafio desafioCriado = desafioRepository.adicionarDesafio(desafioEntity, usuario);
            desafioDTO = objectMapper.convertValue(desafioCriado, DesafioDTO.class);
            return desafioDTO;
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DesafioDTO> list() throws BancoDeDadosException {
        return desafioRepository.listar()
                .stream()
                .map(desafio -> objectMapper.convertValue(desafio, DesafioDTO.class))
                .toList();
    }

    public List<DesafioDTO> listByUser(Integer id) throws BancoDeDadosException {
        return desafioRepository.listarPorUsuario(id)
                .stream()
                .map(desafio -> objectMapper.convertValue(desafio, DesafioDTO.class))
                .toList();

    }

    //Erro no update
    public DesafioDTO update(Integer id,
                           DesafioCreateDTO desafioAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        Desafio desafioEntity = objectMapper.convertValue(desafioAtualizar, Desafio.class);
        usuarioService.findById(desafioEntity.getUsuario().getIdUsuario());
        Desafio desafio = findById(id);
        desafio.setIdDesafio(desafioEntity.getIdDesafio());
        desafio.setResposta(desafioEntity.getResposta());
        desafio.setPergunta(desafioEntity.getPergunta());
        desafio.setUsuario(desafioEntity.getUsuario());
        DesafioDTO desafioDTO = objectMapper.convertValue(desafioRepository.editar(id,desafio), DesafioDTO.class);
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
