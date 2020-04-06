package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Loan;
import jdbc.DBConfig;
import jdbc.DBConnection;
import model.enums.LoanStatus;

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
                + "excluded) "
                + "VALUES(?,?,?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
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
                    BorrowedBooksDAO.create(loan, book);
                } catch (SQLException ex) {
                    Logger.getLogger(LoanDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return loan.getId();
    }

    public static Loan retrieveExcluded(int PkLoan, boolean excluded) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM loans WHERE pk_loan=? AND excluded=?");
        stm.setInt(1, PkLoan);
        stm.setBoolean(2, excluded);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Loan aux = new Loan(
                rs.getInt("pk_loan"),
                PersonDAO.retrieve(rs.getInt("fk_person")),
                rs.getInt("number_renewals"),
                rs.getDate("delivery_date"),
                rs.getDate("delivered_date"),
                rs.getDouble("late_fee"),
                LoanStatus.getById(rs.getInt("status"))
        );
        ArrayList<Integer> arrayAux = BorrowedBooksDAO.retrieveAllForEntityPerson(PkLoan);
        if (!arrayAux.isEmpty()) {
            arrayAux.forEach(pkBook -> {
                try {
                    aux.addBook(BookDAO.retrieve(pkBook));
                } catch (SQLException ex) {
                    Logger.getLogger(LoanDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return aux;
    }

    public static Loan retrieve(int PkLoan) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM loans WHERE pk_loan=?");
        stm.setInt(1, PkLoan);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Loan aux = new Loan(
                rs.getInt("pk_loan"),
                PersonDAO.retrieve(rs.getInt("fk_person")),
                rs.getInt("number_renewals"),
                rs.getDate("delivery_date"),
                rs.getDate("delivered_date"),
                rs.getDouble("late_fee"),
                LoanStatus.getById(rs.getInt("status"))
        );
        ArrayList<Integer> arrayAux = BorrowedBooksDAO.retrieveAllForEntityPerson(PkLoan);
        if (!arrayAux.isEmpty()) {
            arrayAux.forEach(pkBook -> {
                try {
                    aux.addBook(BookDAO.retrieve(pkBook));
                } catch (SQLException ex) {
                    Logger.getLogger(LoanDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return aux;
    }

    public static ArrayList<Loan> retrieveAllExcluded(boolean excluded) throws SQLException {
        ArrayList<Loan> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM loans WHERE excluded=" + excluded);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Loan temp = new Loan(
                    rs.getInt("pk_loan"),
                    PersonDAO.retrieve(rs.getInt("fk_person")),
                    rs.getInt("number_renewals"),
                    rs.getDate("delivery_date"),
                    rs.getDate("delivered_date"),
                    rs.getDouble("late_fee"),
                    LoanStatus.getById(rs.getInt("status"))
            );
            ArrayList<Integer> arrayAux = BorrowedBooksDAO.retrieveAllForEntityPerson(temp.getId());
            if (!arrayAux.isEmpty()) {
                arrayAux.forEach(pkBook -> {
                    try {
                        temp.addBook(BookDAO.retrieve(pkBook));
                    } catch (SQLException ex) {
                        Logger.getLogger(LoanDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Loan> retrieveAll() throws SQLException {
        ArrayList<Loan> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM loans");
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Loan temp = new Loan(
                    rs.getInt("pk_loan"),
                    PersonDAO.retrieve(rs.getInt("fk_person")),
                    rs.getInt("number_renewals"),
                    rs.getDate("delivery_date"),
                    rs.getDate("delivered_date"),
                    rs.getDouble("late_fee"),
                    LoanStatus.getById(rs.getInt("status"))
            );
            ArrayList<Integer> arrayAux = BorrowedBooksDAO.retrieveAllForEntityPerson(temp.getId());
            if (!arrayAux.isEmpty()) {
                arrayAux.forEach(pkBook -> {
                    try {
                        temp.addBook(BookDAO.retrieve(pkBook));
                    } catch (SQLException ex) {
                        Logger.getLogger(LoanDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static void update(Loan loan) throws SQLException {
        if (loan.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        java.sql.Date dateSql = new java.sql.Date(loan.getDeliveryDate().getTime());
        java.sql.Date dateSql2 = new java.sql.Date(loan.getDeliveredDate().getTime());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE loans SET "
                + "fk_person=?, "
                + "number_renewals=?, "
                + "delivery_date=?, "
                + "delivered_date=?, "
                + "late_fee=?, "
                + "status=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE pk_loan=?");
        stm.setInt(1, loan.getPerson().getId());
        stm.setInt(2, loan.getNumberRenewals());
        stm.setDate(3, dateSql);
        stm.setDate(4, dateSql2);
        stm.setDouble(5, loan.getLateFee());
        stm.setInt(6, loan.getStatus().getId());
        stm.setTimestamp(7, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(8, DBConfig.idUserLogged);
        stm.setInt(9, loan.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Loan loan) throws SQLException {
        if (loan.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE loans SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE pk_loan=?");
        stm.setBoolean(1, true);
        stm.setTimestamp(2, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, loan.getId());
        stm.execute();
        stm.close();
        loan.setExcluded(true);
    }

    public static void delete(Loan loan) throws SQLException {
        if (loan.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        BorrowedBooksDAO.delete(loan.getId());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM loans WHERE pk_loan=?");
        stm.setInt(1, loan.getId());
        stm.execute();
        stm.close();
    }

}
