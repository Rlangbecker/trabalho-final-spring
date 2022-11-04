package com.vemser.geekers.repository;

import com.vemser.geekers.config.ConexaoBancoDeDados;
import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class DesafioRepository implements Repositorio<Integer,Desafio>{
    private final ConexaoBancoDeDados conexaoBancoDeDados;
    private AtomicInteger COUNTER = new AtomicInteger();
    private final UsuarioService usuarioService;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_DESAFIO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Desafio adicionar(Desafio object) {
        return null;
    }

    public Desafio adicionarDesafio(Desafio desafio, UsuarioEntity usuarioEntity) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            desafio.setIdDesafio(proximoId);

            String sql = "INSERT INTO DESAFIO\n" +
                    "(id_desafio, pergunta, resposta, id_usuario)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, desafio.getIdDesafio());
            stmt.setString(2, desafio.getPergunta());
            stmt.setInt(3, desafio.getResposta()); // Resposta 0/1
            stmt.setInt(4, usuarioEntity.getIdUsuario()); //Resolver o bug de nao aparecer o usuario

            int res = stmt.executeUpdate();
            System.out.println("adicionarDesafio.res=" + res);
            return desafio;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        finally {
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM DESAFIO WHERE ID_DESAFIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerDesafioPorId.res=" + res);

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
    public boolean editar(Integer id,Desafio desafio) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE DESAFIO SET PERGUNTA = ?, RESPOSTA = ? WHERE ID_DESAFIO = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, desafio.getPergunta());
            stmt.setInt(2, desafio.getResposta());
            stmt.setInt(3, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarDesafio.res=" + res);

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
    public List<Desafio> listar() throws BancoDeDadosException {
        List<Desafio> desafios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM DESAFIO";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Desafio desafio = getRespostaFromResultSet(res);
                desafios.add(desafio);
            }
            return desafios;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
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

    public Desafio listarById(Integer id) throws BancoDeDadosException {
        Connection con = null;
        Desafio desafio = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM DESAFIO WHERE id_desafio = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                desafio = getRespostaFromResultSet(res);

            }
           return desafio;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
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
    
    public Desafio listarByUser(Integer id) throws BancoDeDadosException {
        Connection con = null;
        Desafio desafio = null;
        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT * FROM DESAFIO WHERE id_usuario = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                desafio = getRespostaFromResultSet(res);
               
            }
            return desafio;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
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


    private Desafio getRespostaFromResultSet(ResultSet res) throws SQLException, RegraDeNegocioException {
        Desafio desafio = new Desafio();
        desafio.setIdDesafio(res.getInt("id_desafio"));
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(res.getInt("id_usuario"));
        UsuarioEntity userAlterado = usuarioService.findById(usuarioEntity.getIdUsuario());
        usuarioEntity.setEmail(userAlterado.getEmail());
        usuarioEntity.setSexo(userAlterado.getSexo());
        usuarioEntity.setNome(userAlterado.getNome());
        usuarioEntity.setSenha(userAlterado.getSenha());
        usuarioEntity.setDataNascimento(userAlterado.getDataNascimento());
        usuarioEntity.setTelefone(userAlterado.getTelefone());
        desafio.setUsuarioEntity(usuarioEntity);
        desafio.setPergunta(res.getString("pergunta"));
        desafio.setResposta(res.getInt("resposta"));
        return desafio;
    }

    public List<Desafio> listarPorUsuario(Integer idUsuario) throws BancoDeDadosException {
        List<Desafio> desafios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT D.*, " +
                    "            U.NOME AS NOME_USUARIO " +
                    "       FROM DESAFIO D " +
                    " INNER JOIN USUARIO U ON (D.ID_USUARIO = U.ID_USUARIO) " +
                    "      WHERE D.ID_USUARIO = ? ";

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Desafio desafio = getRespostaFromResultSet(res);
                desafios.add(desafio);
            }
            return desafios;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
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
}
