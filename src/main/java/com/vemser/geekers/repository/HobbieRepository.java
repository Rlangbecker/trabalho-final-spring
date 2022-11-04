package com.vemser.geekers.repository;

import com.vemser.geekers.config.ConexaoBancoDeDados;
import com.vemser.geekers.entity.Hobbie;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.BancoDeDadosException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HobbieRepository implements Repositorio<Integer, Hobbie> {

    private ConexaoBancoDeDados conexaoBancoDeDados;

    public HobbieRepository(ConexaoBancoDeDados conexaoBancoDeDados){
        this.conexaoBancoDeDados=conexaoBancoDeDados;
    }

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_HOBBIE.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Hobbie adicionar(Hobbie hobbie) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            hobbie.setIdHobbies(proximoId);

            String sql = "INSERT INTO HOBBIE \n" +
                    "(id_hobbie , tipo_hobbie , descricao ,id_usuario ) \n" +
                    "VALUES(?, ?, ?, ?) \n";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, hobbie.getIdHobbies());
            stmt.setString(2, hobbie.getTipoHobbie());
            stmt.setString(3, hobbie.getDescricao());
            stmt.setInt(4, hobbie.getIdUsuario());

            int res = stmt.executeUpdate();
            System.out.println("adicionarHobbie.res=" + res);
            return hobbie;
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM HOBBIE WHERE id_hobbie = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            System.out.println("removerHobbiePorId.res = " + res);

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
    public boolean editar(Integer id, Hobbie hobbie) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE HOBBIE SET ");

            if (hobbie.getTipoHobbie() != null) {
                sql.append(" tipo_hobbie  = ? ,");
            }
            if (hobbie.getDescricao() != null) {
                sql.append(" descricao = ? ,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_hobbie = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, hobbie.getTipoHobbie());
            stmt.setString(2, hobbie.getDescricao());
            stmt.setInt(3, id);

            int res = stmt.executeUpdate();
            System.out.println("editarHobbie.res = " + res);

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
    public List<Hobbie> listar() throws BancoDeDadosException {
        List<Hobbie> hobbies = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Statement stmt = con.createStatement();

            String sql = "SELECT H.*, U.NOME AS NOME_USUARIO "
                    + "FROM HOBBIE H "
                    + "LEFT JOIN USUARIO U "
                    + "ON (U.ID_USUARIO = H.ID_USUARIO)";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Hobbie hobbie = getHobbieFromResultSet(res);
                hobbies.add(hobbie);
            }

            return hobbies;
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


    public List<Hobbie> listHobbieByIdUsuario(Integer idUsuario) throws BancoDeDadosException {
        List<Hobbie> hobbies = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM HOBBIE WHERE ID_USUARIO = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Hobbie hobbie = getHobbieFromResultSet(res);
                hobbies.add(hobbie);
            }
            return hobbies;
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


    private Hobbie getHobbieFromResultSet(ResultSet res) throws SQLException {
        Hobbie hobbie = new Hobbie();
        hobbie.setIdHobbies(res.getInt("id_hobbie"));
        hobbie.setTipoHobbie(res.getString("tipo_hobbie"));
        hobbie.setDescricao(res.getString("descricao"));
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(res.getInt("id_usuario"));
        hobbie.setIdUsuario(usuarioEntity.getIdUsuario());
        return hobbie;
    }
}
