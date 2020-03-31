package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Adress;
import model.Phone;
import model.Publisher;
import model.dao.DBConfig;
import model.dao.DBConnection;

public class PublisherDAO {

    public static int create(Publisher publisher) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO publishers "
                + "(company_name, "
                + "trading_name, "
                + "cnpj, "
                + "email, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES(?,?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, publisher.getCompanyName());
        stm.setString(2, publisher.getTradingName());
        stm.setString(3, publisher.getCnpj());
        stm.setString(4, publisher.getEmail());
        stm.setTimestamp(5, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(6, DBConfig.idUserLogged);
        stm.setBoolean(7, publisher.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        publisher.setId(rs.getInt("pk_publisher"));
        publisher.getAdresses().forEach(adress -> {
            try {
                AdressDAO.create(adress, publisher.getId(), 4);
            } catch (SQLException ex) {
                Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        publisher.getPhones().forEach(phone -> {
            try {
                PhoneDAO.create(phone, publisher.getId(), 4);
            } catch (SQLException ex) {
                Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return publisher.getId();
    }

    public static Publisher retrieveExcluded(int pkPublisher, boolean excluded) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM publishers WHERE pk_publisher=? AND excluded=?");
        stm.setInt(1, pkPublisher);
        stm.setBoolean(2, excluded);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Publisher aux = new Publisher(
                rs.getInt("pk_publisher"),
                rs.getString("company_name"),
                rs.getString("trading_name"),
                rs.getString("cnpj"),
                rs.getString("email")
        );
        ArrayList<Adress> auxAdresses = new ArrayList<>();
        auxAdresses = AdressDAO.retrieveAllForEntityPerson(pkPublisher, 4);
        auxAdresses.forEach(adress -> {
            aux.addAdress(adress);
        });

        ArrayList<Phone> auxPhones = new ArrayList<>();
        auxPhones = PhoneDAO.retrieveAllForEntityPerson(pkPublisher, 4);
        auxPhones.forEach(phone -> {
            aux.addPhone(phone);
        });
        return aux;
    }

    public static Publisher retrieve(int pkPublisher) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM publishers WHERE pk_publisher=?");
        stm.setInt(1, pkPublisher);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Publisher aux = new Publisher(
                rs.getInt("pk_publisher"),
                rs.getString("company_name"),
                rs.getString("trading_name"),
                rs.getString("cnpj"),
                rs.getString("email")
        );
        ArrayList<Adress> auxAdresses = new ArrayList<>();
        auxAdresses = AdressDAO.retrieveAllForEntityPerson(pkPublisher, 4);
        auxAdresses.forEach(adress -> {
            aux.addAdress(adress);
        });

        ArrayList<Phone> auxPhones = new ArrayList<>();
        auxPhones = PhoneDAO.retrieveAllForEntityPerson(pkPublisher, 4);
        auxPhones.forEach(phone -> {
            aux.addPhone(phone);
        });
        return aux;
    }

    public static ArrayList<Publisher> retrieveAllExcluded(boolean excluded) throws SQLException {
        ArrayList<Publisher> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM publishers WHERE excluded=" + excluded);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Publisher temp = new Publisher(
                    rs.getInt("pk_publisher"),
                    rs.getString("company_name"),
                    rs.getString("trading_name"),
                    rs.getString("cnpj"),
                    rs.getString("email")
            );
            ArrayList<Adress> auxAdresses = new ArrayList<>();
            auxAdresses = AdressDAO.retrieveAllForEntityPerson(temp.getId(), 4);
            auxAdresses.forEach(adress -> {
                temp.addAdress(adress);
            });

            ArrayList<Phone> auxPhones = new ArrayList<>();
            auxPhones = PhoneDAO.retrieveAllForEntityPerson(temp.getId(), 4);
            auxPhones.forEach(phone -> {
                temp.addPhone(phone);
            });
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Publisher> retrieveAll() throws SQLException {
        ArrayList<Publisher> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM publishers");
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Publisher temp = new Publisher(
                    rs.getInt("pk_publisher"),
                    rs.getString("company_name"),
                    rs.getString("trading_name"),
                    rs.getString("cnpj"),
                    rs.getString("email")
            );
            ArrayList<Adress> auxAdresses = new ArrayList<>();
            auxAdresses = AdressDAO.retrieveAllForEntityPerson(temp.getId(), 4);
            auxAdresses.forEach(adress -> {
                temp.addAdress(adress);
            });

            ArrayList<Phone> auxPhones = new ArrayList<>();
            auxPhones = PhoneDAO.retrieveAllForEntityPerson(temp.getId(), 4);
            auxPhones.forEach(phone -> {
                temp.addPhone(phone);
            });
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static void update(Publisher publisher) throws SQLException {
        if (publisher.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        publisher.getPhones().forEach(phone -> {
            try {
                PhoneDAO.update(phone, publisher.getId(), 4);
            } catch (SQLException ex) {
                Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        publisher.getAdresses().forEach(adress -> {
            try {
                AdressDAO.update(adress, publisher.getId(), 4);
            } catch (SQLException ex) {
                Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE publishers SET "
                + "company_name=?, "
                + "trading_name=?, "
                + "cnpj=?, "
                + "email=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE pk_publisher=?");
        stm.setString(1, publisher.getCompanyName());
        stm.setString(2, publisher.getTradingName());
        stm.setString(3, publisher.getCnpj());
        stm.setString(4, publisher.getEmail());
        stm.setTimestamp(5, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(6, DBConfig.idUserLogged);
        stm.setInt(7, publisher.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Publisher publisher) throws SQLException {
        if (publisher.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        publisher.getPhones().forEach(phone -> {
            try {
                PhoneDAO.updateExcluded(phone, 4);
            } catch (SQLException ex) {
                Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        publisher.getAdresses().forEach(adress -> {
            try {
                AdressDAO.updateExcluded(adress, 4);
            } catch (SQLException ex) {
                Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE publishers SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE pk_publisher=?");
        stm.setBoolean(1, true);
        stm.setTimestamp(2, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, publisher.getId());
        stm.execute();
        stm.close();
        publisher.setExcluded(true);
    }

    public static void delete(Publisher publisher) throws SQLException {
        if (publisher.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        publisher.getPhones().forEach(phone -> {
            try {
                PhoneDAO.delete(phone, 4);
            } catch (SQLException ex) {
                Logger.getLogger(PublisherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        publisher.getAdresses().forEach(adress -> {
            try {
                AdressDAO.delete(adress, 4);
            } catch (SQLException ex) {
                Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM publishers WHERE pk_publisher=?");
        stm.setInt(1, publisher.getId());
        stm.execute();
        stm.close();
    }

}
