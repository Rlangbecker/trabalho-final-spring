package com.vemser.geekers.repository;

import com.vemser.geekers.dto.UsuarioDesafioDTO;
import com.vemser.geekers.dto.UsuarioMatchDTO;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    List<UsuarioEntity> findUsuarioEntityByNome(String nome);

    UsuarioEntity findByEmail(String email);

    List<UsuarioEntity> findByAtivo(TipoAtivo tipoAtivo);

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


    @Query(value = "UPDATE USUARIO_CARGO u " +
            "SET u.ID_CARGO = 3 " +
            "WHERE u.ID_USUARIO = ?1",
    nativeQuery = true)
    void alterarCargo(@Param("idUsuario") Integer idUsuario);

    Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha);

    Optional<UsuarioEntity> findByLogin(String login);
}
