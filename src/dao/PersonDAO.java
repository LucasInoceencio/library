package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;
import model.Phone;
import jdbc.DBConfig;
import jdbc.DBConnection;
import model.Adress;

public class PersonDAO {

    public static int create(Person person) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO persons "
                + "(name, "
                + "cpf, "
                + "email, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES(?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, person.getName());
        stm.setString(2, person.getCpf());
        stm.setString(3, person.getEmail());
        stm.setTimestamp(4, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setBoolean(6, person.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        person.setId(rs.getInt("pk_person"));
        if (!person.getAdresses().isEmpty()) {
            person.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.create(adress, person.getId(), 3);
                } catch (SQLException ex) {
                    Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!person.getPhones().isEmpty()) {
            person.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.create(phone, person.getId(), 3);
                } catch (SQLException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return person.getId();
    }

    public static Person retrieveExcluded(int pkPerson, boolean excluded) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM persons WHERE pk_person=? AND excluded=?");
        stm.setInt(1, pkPerson);
        stm.setBoolean(2, excluded);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Person aux = new Person(
                rs.getInt("pk_person"),
                rs.getString("name"),
                rs.getString("cpf"),
                rs.getString("email")
        );
        ArrayList<Adress> auxAdresses = new ArrayList<>();
        auxAdresses = AdressDAO.retrieveAllForEntityPerson(pkPerson, 3);
        auxAdresses.forEach(adress -> {
            aux.addAdress(adress);
        });

        ArrayList<Phone> auxphones = new ArrayList<>();
        auxphones = PhoneDAO.retrieveAllForEntityPerson(pkPerson, 3);
        auxphones.forEach(phone -> {
            aux.addPhone(phone);
        });
        return aux;
    }

    public static Person retrieve(int pkPerson) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM persons WHERE pk_person=?");
        stm.setInt(1, pkPerson);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        Person aux = new Person(
                rs.getInt("pk_person"),
                rs.getString("name"),
                rs.getString("cpf"),
                rs.getString("email")
        );
        ArrayList<Adress> auxAdresses = new ArrayList<>();
        auxAdresses = AdressDAO.retrieveAllForEntityPerson(pkPerson, 3);
        auxAdresses.forEach(adress -> {
            aux.addAdress(adress);
        });

        ArrayList<Phone> auxphones = new ArrayList<>();
        auxphones = PhoneDAO.retrieveAllForEntityPerson(pkPerson, 3);
        auxphones.forEach(phone -> {
            aux.addPhone(phone);
        });
        return aux;
    }

    public static ArrayList<Person> retrieveAllExcluded(boolean excluded) throws SQLException {
        ArrayList<Person> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM persons WHERE excluded=" + excluded);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Person temp = new Person(
                    rs.getInt("pk_person"),
                    rs.getString("name"),
                    rs.getString("cpf"),
                    rs.getString("email")
            );
            ArrayList<Adress> auxAdresses = new ArrayList<>();
            auxAdresses = AdressDAO.retrieveAllForEntityPerson(temp.getId(), 3);
            auxAdresses.forEach(adress -> {
                temp.addAdress(adress);
            });

            ArrayList<Phone> auxphones = new ArrayList<>();
            auxphones = PhoneDAO.retrieveAllForEntityPerson(temp.getId(), 3);
            auxphones.forEach(phone -> {
                temp.addPhone(phone);
            });
            aux.add(temp);
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Person> retrieveAll() throws SQLException {
        ArrayList<Person> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM persons");
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            Person temp = new Person(
                    rs.getInt("pk_person"),
                    rs.getString("name"),
                    rs.getString("cpf"),
                    rs.getString("email")
            );
            ArrayList<Adress> auxAdresses = new ArrayList<>();
            auxAdresses = AdressDAO.retrieveAllForEntityPerson(temp.getId(), 3);
            auxAdresses.forEach(adress -> {
                temp.addAdress(adress);
            });

            ArrayList<Phone> auxphones = new ArrayList<>();
            auxphones = PhoneDAO.retrieveAllForEntityPerson(temp.getId(), 3);
            auxphones.forEach(phone -> {
                temp.addPhone(phone);
            });
        } while (rs.next());
        return aux;
    }

    public static void update(Person person) throws SQLException {
        if (person.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        if (person.getPhones().isEmpty()) {
            person.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.update(phone, person.getId(), 3);
                } catch (SQLException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!person.getAdresses().isEmpty()) {
            person.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.update(adress, person.getId(), 3);
                } catch (SQLException ex) {
                    Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE persons SET "
                + "name=?, "
                + "cpf=?, "
                + "email=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE pk_person=?");
        stm.setString(1, person.getName());
        stm.setString(2, person.getCpf());
        stm.setString(3, person.getEmail());
        stm.setTimestamp(4, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setInt(6, person.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Person person) throws SQLException {
        if (person.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        if (!person.getPhones().isEmpty()) {
            person.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.updateExcluded(phone, 3);
                } catch (SQLException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!person.getAdresses().isEmpty()) {
            person.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.updateExcluded(adress, 3);
                } catch (SQLException ex) {
                    Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE persons SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE pk_person=?");
        stm.setBoolean(1, true);
        stm.setTimestamp(2, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, person.getId());
        stm.execute();
        stm.close();
        person.setExcluded(true);
    }

    public static void delete(Person person) throws SQLException {
        if (person.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        if (!person.getPhones().isEmpty()) {
            person.getPhones().forEach(phone -> {
                try {
                    PhoneDAO.delete(phone, 3);
                } catch (SQLException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!person.getAdresses().isEmpty()) {
            person.getAdresses().forEach(adress -> {
                try {
                    AdressDAO.delete(adress, 3);
                } catch (SQLException ex) {
                    Logger.getLogger(LegalPersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM persons WHERE pk_person=?");
        stm.setInt(1, person.getId());
        stm.execute();
        stm.close();
    }
}
