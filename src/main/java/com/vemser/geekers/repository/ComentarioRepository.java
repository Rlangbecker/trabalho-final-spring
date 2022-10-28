package com.vemser.geekers.repository;

import com.vemser.geekers.config.ConexaoBancoDeDados;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.Comentario;
import com.vemser.geekers.entity.Usuario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.UsuarioService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ComentarioRepository implements Repositorio<Integer, Comentario>{

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    private final UsuarioService usuarioService;
    private AtomicInteger COUNTER = new AtomicInteger();

    public ComentarioRepository(ConexaoBancoDeDados conexaoBancoDeDados, UsuarioService usuarioService) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
        this.usuarioService = usuarioService;
    }

    @Override
    public Integer getProximoId(Connection connection) throws RegraDeNegocioException, SQLException {
        String sql = "SELECT SEQ_COMENTARIO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Comentario adicionar(Comentario comentario) throws RegraDeNegocioException, BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            comentario.setIdComentario(proximoId);

            String sql = "INSERT INTO COMENTARIO\n" +
                    "(id_comentario, comentario, id_usuario)\n" +
                    "VALUES(?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, comentario.getIdComentario());
            stmt.setString(2, comentario.getComentario());
            stmt.setInt(3, comentario.getUsuario().getIdUsuario()); // Resposta 0/1

            int res = stmt.executeUpdate();
            System.out.println("adicionarComentario.res=" + res);
            return comentario;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean editar(Integer id,Comentario comentario) throws BancoDeDadosException, RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE COMENTARIO SET \n");
            Usuario usuario = comentario.getUsuario();

            if (comentario.getComentario() != null) {
                sql.append(" comentario = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_comentario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if (comentario.getComentario() != null) {
                stmt.setString(index++, comentario.getComentario());
            }
            stmt.setInt(index++, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarComentario.res=" + res);

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM COMENTARIO WHERE ID_DESAFIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerComentarioPorId.res=" + res);

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Comentario> listar() throws RegraDeNegocioException, BancoDeDadosException {
        List<Comentario> comentarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT C.*, " +
                    "            U.NOME AS NOME_USUARIO " +
                    "       FROM COMENTARIO C " +
                    "  LEFT JOIN USUARIO U ON (U.ID_USUARIO = C.ID_USUARIO) ";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Comentario comentario = getComentarioFromResultSet(res);
                comentarios.add(comentario);
            }
            return comentarios;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Comentario> listarPorUsuario(Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        List<Comentario> comentarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT C.*, " +
                    "            U.NOME AS NOME_USUARIO " +
                    "       FROM COMENTARIO C " +
                    " INNER JOIN USUARIO U ON (C.ID_USUARIO = U.ID_USUARIO) " +
                    "      WHERE C.ID_USUARIO = ? ";

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Comentario comentario = getComentarioFromResultSet(res);
                comentarios.add(comentario);
            }
            return comentarios;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Comentario getComentarioFromResultSet(ResultSet res) throws SQLException, RegraDeNegocioException {
        Comentario comentario = new Comentario();
        comentario.setIdComentario(res.getInt("id_comentario"));
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(res.getInt("id_usuario"));



        UsuarioDTO usuarioComentario = usuarioService.listarUsuarioPorId(usuario.getIdUsuario());
        usuario.setNome(usuarioComentario.getNome());
        usuario.setEmail(usuarioComentario.getEmail());
        usuario.setTelefone(usuarioComentario.getTelefone());
        usuario.setSenha(usuarioComentario.getSenha());
        usuario.setDataNascimento(usuarioComentario.getDataNascimento());
        usuario.setSexo(usuarioComentario.getSexo());

        comentario.setUsuario(usuario);
        comentario.setComentario(res.getString("comentario"));

        return comentario;
    }

}
