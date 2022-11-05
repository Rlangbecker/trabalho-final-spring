package com.vemser.geekers.repository;

import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DesafioRepository extends JpaRepository<DesafioEntity, Integer> {

    DesafioEntity findDesafioEntityByUsuario(UsuarioEntity usuario);
}
