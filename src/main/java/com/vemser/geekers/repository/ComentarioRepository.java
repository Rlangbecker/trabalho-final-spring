package com.vemser.geekers.repository;

import com.vemser.geekers.entity.ComentarioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Integer> {

    List<ComentarioEntity> findComentarioEntityByUsuario(UsuarioEntity usuario);

}
