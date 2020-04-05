package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Adress;
import jdbc.DBConfig;
import jdbc.DBConnection;

public class AdressDAO {

    private static String[] valuesForConsultInDB(int table) {
        // Array Auxiliar
        String[] values = {"", "", ""};
        switch (table) {
            case 1:
                values[0] = "pk_author_adress";
                values[1] = "fk_author";
                values[2] = "authors_adresses";
                break;
            case 2:
                values[0] = "pk_legal_person_adress";
                values[1] = "fk_legal_person";
                values[2] = "legal_persons_adresses";
                break;
            case 3:
                values[0] = "pk_person_adress";
                values[1] = "fk_person";
                values[2] = "persons_adresses";
                break;
            case 4:
                values[0] = "pk_publisher_adress";
                values[1] = "fk_publisher";
                values[2] = "publishers_adresses";
                break;
            default:
                throw new RuntimeException("Preencher número da tabela corretamente.");
        }
        return values;
    }

    public static int create(Adress adress, int fkEntityPerson, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO " + finalValues[2] + " "
                + "(public_place, "
                + "number, "
                + "neighborhood, "
                + "complement, "
                + "cep, "
                + "city, "
                + "state, "
                + finalValues[1]
                + ", "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, adress.getPublicPlace());
        stm.setInt(2, adress.getNumber());
        stm.setString(3, adress.getNeighborhood());
        stm.setString(4, adress.getComplement());
        stm.setString(5, adress.getCep());
        stm.setString(6, adress.getCity());
        stm.setString(7, adress.getState());
        stm.setInt(8, fkEntityPerson);
        stm.setTimestamp(9, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(10, DBConfig.idUserLogged);
        stm.setBoolean(11, adress.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        adress.setId(rs.getInt(finalValues[0]));
        return adress.getId();
    }

    public static Adress retrieveExcluded(int pkAdress, int table, boolean excluded) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[0] + "=? AND excluded=?");
        stm.setInt(1, pkAdress);
        stm.setBoolean(2, excluded);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        return new Adress(
                rs.getInt(finalValues[0]),
                rs.getString("public_place"),
                rs.getInt("number"),
                rs.getString("neighborhood"),
                rs.getString("complement"),
                rs.getString("cep"),
                rs.getString("city"),
                rs.getString("state")
        );
    }

    public static Adress retrieve(int pkAdress, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[0] + "=?");
        stm.setInt(1, pkAdress);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        return new Adress(
                rs.getInt(finalValues[0]),
                rs.getString("public_place"),
                rs.getInt("number"),
                rs.getString("neighborhood"),
                rs.getString("complement"),
                rs.getString("cep"),
                rs.getString("city"),
                rs.getString("state")
        );
    }

    public static Adress retrieveForEntityPerson(int fkEntityPerson, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[1] + "=" + fkEntityPerson);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        return new Adress(
                rs.getInt(finalValues[0]),
                rs.getString("public_place"),
                rs.getInt("number"),
                rs.getString("neighborhood"),
                rs.getString("complement"),
                rs.getString("cep"),
                rs.getString("city"),
                rs.getString("state")
        );
    }

    public static ArrayList<Adress> retrieveAllExcluded(int table, boolean excluded) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        ArrayList<Adress> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2] + " WHERE excluded=" + excluded);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(new Adress(
                    rs.getInt(finalValues[0]),
                    rs.getString("public_place"),
                    rs.getInt("number"),
                    rs.getString("neighborhood"),
                    rs.getString("complement"),
                    rs.getString("cep"),
                    rs.getString("city"),
                    rs.getString("state")
            ));
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Adress> retrieveAll(int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        ArrayList<Adress> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2]);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(new Adress(
                    rs.getInt("pk_adress"),
                    rs.getString("public_place"),
                    rs.getInt("number"),
                    rs.getString("neighborhood"),
                    rs.getString("complement"),
                    rs.getString("cep"),
                    rs.getString("city"),
                    rs.getString("state")
            ));
        } while (rs.next());
        return aux;
    }

    public static ArrayList<Adress> retrieveAllForEntityPerson(int fkEntityPerson, int table) throws SQLException {
        String[] finalValues = valuesForConsultInDB(table);
        ArrayList<Adress> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + finalValues[2] + " WHERE " + finalValues[1] + "=" + fkEntityPerson);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        do {
            aux.add(new Adress(
                    rs.getInt(finalValues[0]),
                    rs.getString("public_place"),
                    rs.getInt("number"),
                    rs.getString("neighborhood"),
                    rs.getString("complement"),
                    rs.getString("cep"),
                    rs.getString("city"),
                    rs.getString("state")
            ));
        } while (rs.next());
        return aux;
    }

    public static void update(Adress adress, int fkEntityPerson, int table) throws SQLException {
        if (adress.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE "
                + finalValues[2]
                + " SET "
                + "public_place=?, "
                + "number=?, "
                + "neighborhood=?, "
                + "complement=?, "
                + "cep=?, "
                + "city=?, "
                + "state=?, "
                + finalValues[1]
                + "=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE "
                + finalValues[0] + "=?");
        stm.setString(1, adress.getPublicPlace());
        stm.setInt(2, adress.getNumber());
        stm.setString(3, adress.getNeighborhood());
        stm.setString(4, adress.getComplement());
        stm.setString(5, adress.getCep());
        stm.setString(6, adress.getCity());
        stm.setString(7, adress.getState());
        stm.setInt(8, fkEntityPerson);
        stm.setTimestamp(9, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(10, DBConfig.idUserLogged);
        stm.setInt(11, adress.getId());
        stm.execute();
        stm.close();
    }

    public static void updateExcluded(Adress adress, int table) throws SQLException {
        if (adress.getId() == 0) {
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
        stm.setInt(4, adress.getId());
        stm.execute();
        stm.close();
        adress.setExcluded(true);
    }

    public static void delete(Adress adress, int table) throws SQLException {
        if (adress.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        String[] finalValues = valuesForConsultInDB(table);
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM " + finalValues[2] + " WHERE " + finalValues[0] + "=?");
        stm.setInt(1, adress.getId());
        stm.execute();
        stm.close();
    }

}
