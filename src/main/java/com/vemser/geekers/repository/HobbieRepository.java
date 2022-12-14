package com.vemser.geekers.repository;

import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbieRepository extends JpaRepository<HobbieEntity,Integer> {

    HobbieEntity findHobbieEntityByUsuario(UsuarioEntity usuario);

}
