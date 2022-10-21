package com.vemser.geekers.repository;

import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.exception.BancoDeDadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO>{

    Integer getProximoId(Connection connection) throws SQLException;
    OBJETO adicionar(OBJETO object) throws BancoDeDadosException;

    boolean remover(CHAVE id) throws BancoDeDadosException;

    Desafio editar(CHAVE id, OBJETO objeto) throws BancoDeDadosException;

    List<OBJETO> listar() throws BancoDeDadosException;
}