package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Phone;
import model.dao.DBConfig;
import model.dao.DBConnection;

public class PhoneDAO {

    private static String[] valuesForConsultInDB(int table) {
        // Array Auxiliar
        String[] values = {"", "", ""};
        switch (table) {
            case 1:
                values[0] = "pk_author_phone";
                values[1] = "fk_author";
                values[2] = "authors_phones";
                break;
            case 2:
                values[0] = "pk_legal_person_phone";
                values[1] = "fk_legal_person";
                values[2] = "legal_persons_phones";
                break;
            case 3:
                values[0] = "pk_person_phone";
                values[1] = "fk_person";
                values[2] = "persons_phones";
                break;
            case 4:
                values[0] = "pk_publisher_phone";
                values[1] = "fk_publisher";
                values[2] = "publishers_phones";
                break;
            default:
                throw new RuntimeException("Preencher número da tabela corretamente.");
        }
        return values;
    }

    public static int create(Phone phone, int fkEntityPerson, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO " + finalValues[2] + " "
                + "(ddd, "
                + "phone, "
                + finalValues[1]
                + ", "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES (?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, phone.getDdd());
        stm.setString(2, phone.getNumber());
        stm.setInt(3, fkEntityPerson);
        stm.setTimestamp(4, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setBoolean(6, phone.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        phone.setId(rs.getInt(finalValues[0]));
        return phone.getId();
    }

    public static Phone retrieveExcluded(int pkPhone, int table, boolean excluded) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[0] + "=? AND excluded=?");
        stm.setInt(1, pkPhone);
        stm.setBoolean(2, excluded);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        return new Phone(
                rs.getInt(finalValues[0]),
                rs.getString("ddd"),
                rs.getString("phone")
        );
    }

    public static Phone retrieve(int pkPhone, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[0] + "=?");
        stm.setInt(1, pkPhone);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        return new Phone(
                rs.getInt(finalValues[0]),
                rs.getString("ddd"),
                rs.getString("phone")
        );
    }

    public static ArrayList<Phone> retrieveAllExcluded(int table, boolean excluded) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        ArrayList<Phone> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2] + " WHERE excluded=" + excluded);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(new Phone(
                    rs.getInt(finalValues[0]),
                    rs.getString("ddd"),
                    rs.getString("phone")
            ));
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Phone> retrieveAll(int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        ArrayList<Phone> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2]);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(new Phone(
                    rs.getInt(finalValues[0]),
                    rs.getString("ddd"),
                    rs.getString("phone")
            ));
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Phone> retrieveAllForEntityPerson(int fkEntityPerson, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        ArrayList<Phone> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[1] + "=" + fkEntityPerson);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(new Phone(
                    rs.getInt(finalValues[0]),
                    rs.getString("ddd"),
                    rs.getString("phone")
            ));
        } while (rs.next());
        return aux;
    }

    public static void update(Phone phone, int fkEntityPerson, int table) throws SQLException {
        if (phone.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE "
                + finalValues[2]
                + " SET "
                + "ddd=?, "
                + "phone=?, "
                + finalValues[1]
                + "=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE "
                + finalValues[0] + "=?");
        stm.setString(1, phone.getDdd());
        stm.setString(2, phone.getNumber());
        stm.setInt(3, fkEntityPerson);
        stm.setTimestamp(4, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(5, DBConfig.idUserLogged);
        stm.setInt(6, phone.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Phone phone, int table) throws SQLException {
        if (phone.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE "
                + finalValues[2]
                + " SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=? "
                + "WHERE "
                + finalValues[0] + "=?");
        stm.setBoolean(1, true);
        stm.setTimestamp(2, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(3, DBConfig.idUserLogged);
        stm.setInt(4, phone.getId());
        stm.execute();
        stm.close();
        phone.setExcluded(true);
    }

    public static void delete(Phone phone, int table) throws SQLException {
        if (phone.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM " + finalValues[2] + " WHERE " + finalValues[0] + "=?");
        stm.setInt(1, phone.getId());
        stm.execute();
        stm.close();
    }
}
