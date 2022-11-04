package com.vemser.geekers.repository;

import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbieRepository extends JpaRepository<HobbieEntity,Integer> {

   HobbieEntity findHobbieEntityByIdHobbie(Integer idHobbie);

    HobbieEntity findHobbieEntityByUsuario(UsuarioEntity usuario);

}
