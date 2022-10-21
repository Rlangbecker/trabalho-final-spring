package com.vemser.geekers.service;

import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.DesafioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesafioService {

    private DesafioRepository desafioRepository;

    public DesafioService(DesafioRepository desafioRepository){
        this.desafioRepository = desafioRepository;
    }

    public Desafio create(Desafio desafio) {
        Desafio result;
        try {
            result = desafioRepository.adicionar(desafio);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Desafio> list() throws BancoDeDadosException {
        return desafioRepository.listar();
    }

    public Desafio update(Integer id,
                           Desafio desafioAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        return desafioRepository.editar(id,desafioAtualizar);
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
