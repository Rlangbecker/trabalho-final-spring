package com.vemser.geekers.repository;

import com.vemser.geekers.config.ConexaoBancoDeDados;
import com.vemser.geekers.entity.Match;
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
public class MatchRepository implements Repositorio<Integer, Match> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    private AtomicInteger COUNTER = new AtomicInteger();
    private final UsuarioService usuarioService;
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_MATCH.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Match adicionar(Match object) throws BancoDeDadosException, RegraDeNegocioException {
        return null;
    }


    public Match adicionarMatch(Match match) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            match.setIdMatch(proximoId);

            String sql = "INSERT INTO MATCH \n" +
                    "(id_match, id_usuario, id_usuario_main)\n" +
                    "VALUES(?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, match.getIdMatch());
            stmt.setInt(2, match.getUsuario());
            stmt.setInt(3, match.getUsuarioMain());

            int res = stmt.executeUpdate();
            System.out.println("adicionarMatch.res=" + res);
            return match;
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM MATCH WHERE ID_MATCH = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerMatchPorId.res=" + res);

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
    public boolean editar(Integer id,Match match) throws BancoDeDadosException {
        return false;
    }

    @Override
    public List<Match> listar() throws BancoDeDadosException {
        List<Match> matches = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT M.*, " +
                    "            U.NOME AS NOME_PESSOA " +
                    "       FROM MATCH M " +
                    "  LEFT JOIN USUARIO U ON (M.ID_USUARIO = U.ID_USUARIO) ";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Match match = getMatchFromResultSet(res);
                matches.add(match);
            }
            return matches;
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

    public List<Match> listarPorUsuario(Integer idUsuario) throws BancoDeDadosException {
        List<Match> matches = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();


            String sql = "SELECT * FROM MATCH WHERE ID_USUARIO_MAIN = ? ";

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                Match match = getMatchFromResultSet(res);
                matches.add(match);
            }
            return matches;
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
    private Match getMatchFromResultSet(ResultSet res) throws SQLException, RegraDeNegocioException {
        Match match = new Match();
        match.setIdMatch(res.getInt("id_match"));
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        UsuarioEntity usuarioEntity2 = new UsuarioEntity();
        usuarioEntity.setIdUsuario(res.getInt("id_usuario"));
        UsuarioEntity usuarioEntity1 = usuarioService.findById(usuarioEntity.getIdUsuario());
        usuarioEntity.setNome(usuarioEntity1.getNome());
        usuarioEntity2.setIdUsuario(res.getInt("id_usuario_main"));
        UsuarioEntity usuarioEntityLogado = usuarioService.findById(usuarioEntity2.getIdUsuario());
        usuarioEntity2.setNome(usuarioEntityLogado.getNome());
        match.setUsuario(usuarioEntity.getIdUsuario());
        match.setUsuarioMain(usuarioEntity2.getIdUsuario());
        return match;
    }

}
