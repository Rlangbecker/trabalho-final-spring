package com.vemser.geekers.repository;



import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO>{

    Integer getProximoId(Connection connection) throws SQLException;
    OBJETO create(OBJETO object) throws RegraDeNegocioException,BancoDeDadosException;

    boolean remover(CHAVE id) throws RegraDeNegocioException, BancoDeDadosException;

    boolean editar(CHAVE id, OBJETO objeto) throws RegraDeNegocioException, BancoDeDadosException;

    List<OBJETO> listar() throws RegraDeNegocioException, BancoDeDadosException;
}

