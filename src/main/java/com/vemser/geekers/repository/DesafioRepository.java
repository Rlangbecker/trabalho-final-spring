package com.vemser.geekers.repository;

import com.vemser.geekers.config.ConexaoBancoDeDados;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public interface DesafioRepository extends JpaRepository<DesafioEntity, Integer> {

}
