
package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import model.dao.DBConnection;

public class UserDAO {
    
    public static int create(User user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO users(username, password_user, person) VALUES(?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, user.getUser());
        stm.setString(2, user.getPassword());
        stm.setLong(3, user.getPersonID());
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        user.setId(rs.getInt("pk_user"));
        return user.getId();
    }
    
}
