package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.User;
import model.dao.DBConfig;
import model.dao.DBConnection;

public class UserDAO {

    public static int create(User user) throws SQLException {
        Date d = new Date();
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO users "
                + "(username, "
                + "password_user, "
                + "person, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES(?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getPassword());
        stm.setInt(3, user.getPersonId());
        stm.setDate(4, (java.sql.Date) d);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setBoolean(6, user.isExcluded());
        stm.execute();

        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        user.setId(rs.getInt("pk_user"));
        return user.getId();
    }

    public static User retrieve(int pkUser, boolean excluded) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement stm = conn.prepareStatement("SELECT * FROM users WHERE pk_user=? AND excluded=?");
        stm.setInt(1, pkUser);
        stm.setBoolean(2, excluded);
        stm.execute();

        ResultSet rs = stm.getResultSet();
        rs.next();
        return new User(
                rs.getInt("pk_user"),
                rs.getString("username"),
                rs.getString("password_user")
        );
    }

    public static ArrayList<User> retrieveAll(boolean excluded) throws SQLException {
        ArrayList<User> aux = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE excluded=" + excluded);
        while (rs.next()) {
            aux.add(new User(
                    rs.getInt("pk_user"),
                    rs.getString("username"),
                    rs.getString("password_user")));
        }
        return aux;
    }

    public static void update(User user) throws SQLException {
        if (user.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Date d = new Date();
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE users SET "
                + "username=?, "
                + "password=?, "
                + "person=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE pk_user=?");
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getPassword());
        stm.setInt(3, user.getPersonId());
        stm.setDate(4, (java.sql.Date) d);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setInt(6, user.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(User user) throws SQLException {
        if (user.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Date d = new Date();
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE users SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE pk_user=?");
        stm.setBoolean(1, true);
        stm.setDate(2, (java.sql.Date) d);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, user.getId());
        stm.execute();
        stm.close();
        user.setExcluded(true);
    }

    public static void delete(User user) throws SQLException {
        if (user.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE * FROM users WHERE pk_user=?");
        stm.setInt(1, user.getId());
        stm.execute();
        stm.close();
    }
}
