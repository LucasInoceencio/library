package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Loan;
import model.dao.DBConfig;
import model.dao.DBConnection;

public class LoanDAO {

    public static int create(Loan loan) throws SQLException {
        if (loan.getBooks().size() > 3) {
            throw new IllegalArgumentException("A quantidade máxima (3) de livro por empréstimo foi atingida!");
        }
        java.sql.Date dateSql = new java.sql.Date(loan.getDeliveryDate().getTime());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO loans "
                + "(fk_person, "
                + "number_renewals, "
                + "delivery_date, "
                + "late_fee, "
                + "status, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded "
                + "VALUES(?,?,?,?,?,?,?,?)");
        stm.setInt(1, loan.getPerson().getId());
        stm.setInt(2, loan.getNumberRenewals());
        stm.setDate(3, dateSql);
        stm.setDouble(4, loan.getLateFee());
        stm.setInt(5, loan.getStatus().getId());
        stm.setTimestamp(6, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(7, DBConfig.idUserLogged);
        stm.setBoolean(8, loan.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        loan.setId(rs.getInt("pk_loan"));
        if (!loan.getBooks().isEmpty()) {
            loan.getBooks().forEach(book -> {
                try {
                    loan.addIdBorrowedBook(BorrowedBooksDAO.create(loan, book));
                } catch (SQLException ex) {
                    Logger.getLogger(LoanDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return loan.getId();
    }
}
