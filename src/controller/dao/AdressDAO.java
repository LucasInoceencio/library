package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Adress;
import model.dao.DBConfig;
import static model.dao.DBConfig.tzUTC;
import model.dao.DBConnection;

public class AdressDAO {

    public static int create(Adress adress) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO adresses "
                + "(public_place, "
                + "number_adress, "
                + "neighborhood, "
                + "complement, "
                + "cep, "
                + "city, "
                + "state, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, adress.getPublicPlace());
        stm.setInt(2, adress.getNumber());
        stm.setString(3, adress.getNeighborhood());
        stm.setString(4, adress.getComplement());
        stm.setString(5, adress.getCep());
        stm.setString(6, adress.getCity());
        stm.setString(7, adress.getState());
        stm.setTimestamp(8, DBConfig.now(), tzUTC);
        stm.setInt(9, DBConfig.idUserLogged);
        stm.setBoolean(10, adress.isExcluded());
        stm.execute();

        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        adress.setId(rs.getInt("pk_adress"));
        return adress.getId();
    }

    public static Adress retrieve(int pkAdress, boolean excluded) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement stm = conn.prepareStatement("SELECT * FROM adresses WHERE pk_adress=? AND excluded=?");
        stm.setInt(1, pkAdress);
        stm.setBoolean(2, excluded);
        stm.execute();

        ResultSet rs = stm.getResultSet();
        rs.next();
        return new Adress(
                rs.getInt("pk_adress"),
                rs.getString("public_place"),
                rs.getInt("number_adress"),
                rs.getString("neighborhood"),
                rs.getString("complement"),
                rs.getString("cep"),
                rs.getString("city"),
                rs.getString("state")
        );
    }

    public static ArrayList<Adress> retrieveAll(boolean excluded) throws SQLException {
        ArrayList<Adress> aux = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM adresses WHERE excluded=" + excluded);
        while (rs.next()) {
            aux.add(new Adress(
                    rs.getInt("pk_adress"),
                    rs.getString("public_place"),
                    rs.getInt("number_adress"),
                    rs.getString("neighborhood"),
                    rs.getString("complement"),
                    rs.getString("cep"),
                    rs.getString("city"),
                    rs.getString("state")
            ));
        }
        return aux;
    }

    public static void update(Adress adress) throws SQLException {
        if (adress.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE adress SET "
                + "public_place=?, "
                + "number_adress=?, "
                + "neighborhood=?, "
                + "complement=?, "
                + "cep=?, "
                + "city=?, "
                + "state=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=?");
        stm.setString(1, adress.getPublicPlace());
        stm.setInt(2, adress.getNumber());
        stm.setString(3, adress.getNeighborhood());
        stm.setString(4, adress.getComplement());
        stm.setString(5, adress.getCep());
        stm.setString(6, adress.getCity());
        stm.setString(7, adress.getState());
        stm.setTimestamp(8, DBConfig.now(), tzUTC);
        stm.setInt(9, DBConfig.idUserLogged);
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Adress adress) throws SQLException {
        if (adress.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE adresses SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE pk_adress=?");
        stm.setBoolean(1, true);
        stm.setTimestamp(2, DBConfig.now(), tzUTC);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, adress.getId());
        stm.execute();
        stm.close();
        adress.setExcluded(true);
    }

    public static void delete(Adress adress) throws SQLException {
        if (adress.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE * FROM adresses WHERE pk_adress=?");
        stm.setInt(1, adress.getId());
        stm.execute();
        stm.close();
    }

}
