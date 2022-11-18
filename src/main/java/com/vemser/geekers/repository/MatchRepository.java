package com.vemser.geekers.repository;

import com.vemser.geekers.entity.MatchEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {

    List<MatchEntity> findMatchEntitiesByUsuario(UsuarioEntity usuario);
    Integer countByUsuarioMain (Integer usuario);

}
