package com.vemser.geekers.repository;

import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio <CHAVE, OBJETO>{

    Integer getProximoId(Connection connection) throws RegraDeNegocioException;
    OBJETO adicionar(OBJETO object) throws BancoDeDadosException, RegraDeNegocioException;

    boolean remover(CHAVE id) throws RegraDeNegocioException;

    boolean editar(CHAVE id, OBJETO objeto) throws RegraDeNegocioException;

    List<OBJETO> listar() throws RegraDeNegocioException;

}
