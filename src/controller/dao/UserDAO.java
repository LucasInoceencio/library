package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.User;
import model.dao.DBConnection;

public class UserDAO {
    
    public static int create(User user) throws SQLException {
        Date d = new Date();
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO users(username, password_user, person, date_hour_inclusion) VALUES(?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getPassword());
        stm.setInt(3, user.getPersonID());
        stm.setDate(4, (java.sql.Date) d);
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        user.setId(rs.getInt("pk_user"));
        return user.getId();
    }
    
    public static User retrieve(int pkUser) throws SQLException {
        Connection conn = DBConnection.getConnection();
        
        PreparedStatement stm = conn.prepareStatement("select * from users where pk_user=?");
        stm.setInt(1, pkUser);
        stm.execute();
        
        ResultSet rs = stm.getResultSet();
        rs.next();
        return new User(rs.getInt("pk_user"), rs.getString("username"), rs.getString("password_user"));
    }
    
    public static ArrayList<User> retrieveAll() throws SQLException {
        ArrayList<User> aux = new ArrayList<>();
        
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from users");
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
        PreparedStatement stm = conn.prepareStatement("update users set username=?, password=?, person=?, date_hour_alteration=?");
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getPassword());
        stm.setInt(3, user.getPersonID());
        stm.setDate(4, (java.sql.Date) d);
        stm.execute();
        stm.close();
    }
    
    public static void updateExcluded(User user) throws SQLException {
        if (user.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Date d = new Date();
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("update users set excluded=?, date_hour_deleted=?");
        stm.setBoolean(1, true);
        stm.setDate(2, (java.sql.Date) d);
        stm.execute();
        stm.close();
    }
    
    public static void delete(User user) throws SQLException {
        if (user.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("delete * from users where pk_user=?");
        stm.setInt(1, user.getId());
        stm.execute();
        stm.close();
    }
}
