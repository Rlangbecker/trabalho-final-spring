package com.vemser.geekers.repository;

import com.vemser.geekers.dto.UsuarioDesafioDTO;
import com.vemser.geekers.dto.UsuarioMatchDTO;
import com.vemser.geekers.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    List<UsuarioEntity> findUsuarioEntityByNome(String nome);


    @Query(" select new com.vemser.geekers.dto.UsuarioMatchDTO(" +
            " u.idUsuario," +
            " u.nome ," +
            " u.email ," +
            " m.idMatch," +
            " m.usuarioMain " +
            ")" +
            "  from usuario u" +
            " left join u.matchs m " +
            " where (:idUsuario is null or u.idUsuario = :idUsuario)")
    List<UsuarioMatchDTO> listarUsuarioEMatch(Integer idUsuario);

    @Query(" select new com.vemser.geekers.dto.UsuarioDesafioDTO(" +
            " u.idUsuario," +
            " u.nome ," +
            " u.email ," +
            " d.pergunta, " +
            " d.resposta, " +
            " d.idDesafio " +
            ")" +
            "  from usuario u" +
            " left join u.desafio d " +
            " where (:idUsuario is null or u.idUsuario = :idUsuario)")
    List<UsuarioDesafioDTO> listarUsuarioEDesafio(Integer idUsuario);
}
