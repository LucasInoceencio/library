package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Adress;
import model.Author;
import model.Phone;
import model.dao.DBConfig;
import model.dao.DBConnection;

public class AuthorDAO {

    public static int create(Author author) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO authors "
                + "(name, "
                + "cpf, "
                + "email, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES(?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, author.getName());
        stm.setString(2, author.getCpf());
        stm.setString(3, author.getEmail());
        stm.setTimestamp(4, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setBoolean(6, author.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        author.setId(rs.getInt("pk_author"));
        if (!author.getAdresses().isEmpty()) {
            author.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.create(adress, author.getId(), 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!author.getPhones().isEmpty()) {
            author.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.create(phone, author.getId(), 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return author.getId();
    }

    public static Author retrieveExcluded(int pkAuthor, boolean excluded) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM authors WHERE pk_author=? AND excluded=?");
        stm.setInt(1, pkAuthor);
        stm.setBoolean(2, excluded);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Author aux = new Author(
                rs.getInt("pk_author"),
                rs.getString("name"),
                rs.getString("cpf"),
                rs.getString("email")
        );
        ArrayList<Adress> auxAdresses = new ArrayList<>();
        auxAdresses = AdressDAO.retrieveAllForEntityPerson(pkAuthor, 1);
        auxAdresses.forEach(adress -> {
            aux.addAdress(adress);
        });

        ArrayList<Phone> auxPhones = new ArrayList<>();
        auxPhones = PhoneDAO.retrieveAllForEntityPerson(pkAuthor, 1);
        auxPhones.forEach(phone -> {
            aux.addPhone(phone);
        });
        return aux;
    }

    public static Author retrieve(int pkAuthor) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM authors WHERE pk_author=?");
        stm.setInt(1, pkAuthor);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Author aux = new Author(
                rs.getInt("pk_author"),
                rs.getString("name"),
                rs.getString("cpf"),
                rs.getString("email")
        );
        ArrayList<Adress> auxAdresses = new ArrayList<>();
        auxAdresses = AdressDAO.retrieveAllForEntityPerson(pkAuthor, 1);
        auxAdresses.forEach(adress -> {
            aux.addAdress(adress);
        });

        ArrayList<Phone> auxPhones = new ArrayList<>();
        auxPhones = PhoneDAO.retrieveAllForEntityPerson(pkAuthor, 1);
        auxPhones.forEach(phone -> {
            aux.addPhone(phone);
        });
        return aux;
    }

    public static ArrayList<Author> retrieveAllExcluded(boolean excluded) throws SQLException {
        ArrayList<Author> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM authors WHERE excluded=?");
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Author temp = new Author(
                    rs.getInt("pk_author"),
                    rs.getString("name"),
                    rs.getString("cpf"),
                    rs.getString("email")
            );
            ArrayList<Adress> auxAdresses = new ArrayList<>();
            auxAdresses = AdressDAO.retrieveAllForEntityPerson(temp.getId(), 1);
            auxAdresses.forEach(adress -> {
                temp.addAdress(adress);
            });

            ArrayList<Phone> auxPhones = new ArrayList<>();
            auxPhones = PhoneDAO.retrieveAllForEntityPerson(temp.getId(), 1);
            auxPhones.forEach(phone -> {
                temp.addPhone(phone);
            });
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Author> retrieveAll() throws SQLException {
        ArrayList<Author> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM authors");
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Author temp = new Author(
                    rs.getInt("pk_author"),
                    rs.getString("name"),
                    rs.getString("cpf"),
                    rs.getString("email")
            );
            ArrayList<Adress> auxAdresses = new ArrayList<>();
            auxAdresses = AdressDAO.retrieveAllForEntityPerson(temp.getId(), 1);
            auxAdresses.forEach(adress -> {
                temp.addAdress(adress);
            });

            ArrayList<Phone> auxPhones = new ArrayList<>();
            auxPhones = PhoneDAO.retrieveAllForEntityPerson(temp.getId(), 1);
            auxPhones.forEach(phone -> {
                temp.addPhone(phone);
            });
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static void update(Author author) throws SQLException {
        if (author.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        if (!author.getPhones().isEmpty()) {
            author.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.update(phone, author.getId(), 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!author.getAdresses().isEmpty()) {
            author.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.update(adress, author.getId(), 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE authors SET "
                + "name=?, "
                + "cpf=?, "
                + "email=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE pk_author=?");
        stm.setString(1, author.getName());
        stm.setString(2, author.getCpf());
        stm.setString(3, author.getEmail());
        stm.setTimestamp(4, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setInt(6, author.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Author author) throws SQLException {
        if (author.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        if (!author.getPhones().isEmpty()) {
            author.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.updateExcluded(phone, 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!author.getAdresses().isEmpty()) {
            author.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.updateExcluded(adress, 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE authors SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE pk_author=?");
        stm.setBoolean(1, true);
        stm.setTimestamp(2, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, author.getId());
        stm.execute();
        stm.close();
        author.setExcluded(true);
    }

    public static void delete(Author author) throws SQLException {
        if (author.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        if (!author.getPhones().isEmpty()) {
            author.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.delete(phone, 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!author.getAdresses().isEmpty()) {
            author.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.delete(adress, 1);
                } catch (SQLException ex) {
                    Logger.getLogger(AuthorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM authors WHERE pk_author=?");
        stm.setInt(1, author.getId());
        stm.execute();
        stm.close();
    }

}
