package com.vemser.geekers.repository;

import com.vemser.geekers.config.ConexaoBancoDeDados;
import com.vemser.geekers.entity.Usuario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements Repositorio<Integer, Usuario> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public UsuarioRepository(ConexaoBancoDeDados conexaoBancoDeDados) {
        this.conexaoBancoDeDados = conexaoBancoDeDados;
    }

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_USUARIO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            usuario.setIdUsuario(proximoId);

            String sql = "INSERT INTO USUARIO\n" +
                    "(id_usuario, nome, email, telefone, senha, data_nascimento, sexo)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)\n";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getSenha());
            stmt.setDate(6, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(7, usuario.getSexo());
            int res = stmt.executeUpdate();
            System.out.println("adicionarUsuario.res=" + res);
            return usuario;
        } catch (SQLException e) {
            e.printStackTrace();
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
    public List<Usuario> listar() throws BancoDeDadosException, RegraDeNegocioException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("id_usuario"));
                usuario.setNome(res.getString("nome"));
                usuario.setEmail(res.getString("email"));
                usuario.setTelefone(res.getString("telefone"));
                usuario.setSenha(res.getString("senha"));
                usuario.setDataNascimento(res.getDate("data_nascimento").toLocalDate());
                usuario.setSexo(res.getString("sexo"));
                usuarios.add(usuario);

            }
            return usuarios;
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean editar(Integer id, Usuario usuario) throws BancoDeDadosException, RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE USUARIO SET ");
            sql.append(" nome  = ?, ");
            sql.append(" email = ?, ");
            sql.append(" telefone = ?, ");
            sql.append(" senha = ?, ");
            sql.append(" data_nascimento = ?, ");
            sql.append(" sexo = ? ");
            sql.append(" WHERE id_usuario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(6, usuario.getSexo());
            stmt.setInt(7, id);

            int res = stmt.executeUpdate();
            System.out.println("editarUsuario.res = " + res);

            return res > 0;

        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean remover(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            System.out.println("removerUsuarioPorId.res = " + res);

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


//    public boolean logar(Usuario usuario) {
//        usuario.setLogado(true);
//        return true;
//    }

//    public Usuario receberUsuario(Usuario usuarioLogin) throws BancoDeDadosException, RegraDeNegocioException {
//        Connection con = null;
//        Usuario usuario = new Usuario();
//
//        try {
//            con = conexaoBancoDeDados.getConnection();
//            String sql = "SELECT * FROM USUARIO " +
//                    " WHERE EMAIL = ? AND SENHA = ?";
//
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, usuarioLogin.getEmail());
//            stmt.setString(2, usuarioLogin.getSenha());
//
//            ResultSet res = stmt.executeQuery();
//
//            res.next();
//            usuario.setIdUsuario(res.getInt("id_usuario"));
//            usuario.setNome(res.getString("nome"));
//            usuario.setEmail(res.getString("email"));
//            usuario.setSenha(res.getString("senha"));
//            usuario.setTelefone(res.getString("telefone"));
//            usuario.setDataNascimento(res.getDate("data_nascimento").toLocalDate());
//            usuario.setSexo(res.getString("sexo"));
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return usuario;
//    }

//    public List<Usuario> listarUsuariosRandom(Integer quantidadeUsuarios) throws BancoDeDadosException, RegraDeNegocioException {
//
//        List<Usuario> usuarios = new ArrayList<>();
//        Connection con = null;
//        try {
//            con = conexaoBancoDeDados.getConnection();
//
//
//            String sql = "SELECT * \n" +
//                    "FROM (SELECT * FROM USUARIO u ORDER BY dbms_random.value) \n" +
//                    " WHERE rownum <= ?\n";
//
//            // Executa-se a consulta
//            PreparedStatement stmt = con.prepareStatement(sql);
//
//            stmt.setInt(1, quantidadeUsuarios);
//
//            ResultSet res = stmt.executeQuery();
//
//            while (res.next()) {
//                Usuario usuario = getUsuarioFromResultSet(res);
//                usuarios.add(usuario);
//            }
//            return usuarios;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public List<Usuario> listarUsuarioPorID(Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT * \n" +
                    "FROM USUARIO u \n" +
                    " WHERE u.ID_USUARIO = ?\n";

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Usuario usuario = getUsuarioFromResultSet(res);
                usuarios.add(usuario);
            }
            return usuarios;
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

    public List<Usuario> listarUsuarioPorNome(String nome) throws BancoDeDadosException, RegraDeNegocioException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT * \n" +
                    "FROM USUARIO u \n" +
                    " WHERE u.NOME = ?\n";

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, nome);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Usuario usuario = getUsuarioFromResultSet(res);
                usuarios.add(usuario);
            }
            return usuarios;
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

    private Usuario getUsuarioFromResultSet(ResultSet res) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(res.getInt("id_usuario"));
        usuario.setNome(res.getString("nome"));
        usuario.setEmail(res.getString("email"));
        usuario.setTelefone(res.getString("telefone"));
        usuario.setSenha(res.getString("senha"));
        usuario.setDataNascimento(res.getDate("data_nascimento").toLocalDate());
        usuario.setSexo(res.getString("sexo"));
        return usuario;
    }
}
