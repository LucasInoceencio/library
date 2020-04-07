
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Book;
import model.Loan;
import jdbc.DBConfig;
import jdbc.DBConnection;

public class BorrowedBooksDAO {
    
    public static int create(Loan loan, Book book) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO borrowed_books "
                + "(fk_loan, "
                + "fk_book, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES(?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setInt(1, loan.getId());
        stm.setInt(2, book.getId());
        stm.setTimestamp(3, DBConfig.now(), DBConfig.TZ_UTC);
        stm.setInt(4, DBConfig.userLogged.getId());
        stm.setBoolean(5, loan.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        return rs.getInt("pk_borrowed_book");
    }
    
    public static ArrayList<Integer> retrieveAllForEntityPerson(int fkEntityPerson) throws SQLException {
        ArrayList<Integer> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM borrowed_books WHERE fk_loan=" + fkEntityPerson);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(rs.getInt("fk_book"));
        } while (rs.next());
        return aux;
    }
    
    public static void delete(Integer fkLoan) throws SQLException {
        if (fkLoan == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM borrowed_books WHERE fk_loan=?");
        stm.setInt(1, fkLoan);
        stm.execute();
        stm.close();
    }
}
