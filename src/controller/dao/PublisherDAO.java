package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Publisher;
import model.dao.DBConfig;
import model.dao.DBConnection;

public class PublisherDAO {
    
    public static int create(Publisher publisher) throws SQLException {
        int fkAdress = AdressDAO.create(publisher.getAdress());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO publishers "
                + "(company_name, "
                + "trading_name, "
                + "cnpj, "
                + "fk_adress, "
                + "email, "
                + "date_hour_inclusion, "
                + "fk_user_who_included, "
                + "excluded) "
                + "VALUES(?,?,?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, publisher.getCompanyName());
        stm.setString(2, publisher.getTradingName());
        stm.setString(3, publisher.getCnpj());
        stm.setInt(4, fkAdress);
        stm.setString(5, publisher.getEmail());
        stm.setTimestamp(6, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(7, DBConfig.idUserLogged);
        stm.setBoolean(8, publisher.isExcluded());
        stm.execute();
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        publisher.setId(rs.getInt("pk_publisher"));
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
        aux.setAdress(AdressDAO.retrieveExcluded(pkPublisher, excluded));
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
        aux.setAdress(AdressDAO.retrieve(pkPublisher));
        return aux;
    }

    public static ArrayList<Publisher> retrieveAllExcluded(boolean excluded) throws SQLException {
        ArrayList<Publisher> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM publishers WHERE excluded=" + excluded);
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        while (rs.next()) {
            Publisher temp = new Publisher(
                    rs.getInt("pk_publisher"),
                    rs.getString("company_name"),
                    rs.getString("trading_name"),
                    rs.getString("cnpj"),
                    rs.getString("email")
            );
            temp.setAdress(AdressDAO.retrieve(rs.getInt("pk_publisher")));
            aux.add(temp);
        }
        return aux;
    }
    
    public static ArrayList<Publisher> retrieveAll() throws SQLException {
        ArrayList<Publisher> aux = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM publishers");
        if (!rs.next()) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        while (rs.next()) {
            Publisher temp = new Publisher(
                    rs.getInt("pk_publisher"),
                    rs.getString("company_name"),
                    rs.getString("trading_name"),
                    rs.getString("cnpj"),
                    rs.getString("email")
            );
            temp.setAdress(AdressDAO.retrieve(rs.getInt("pk_publisher")));
            aux.add(temp);
        }
        return aux;
    }
    
    public static void update(Publisher publisher) throws SQLException {
        if (publisher.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        AdressDAO.update(publisher.getAdress());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE publishers SET "
                + "company_name=?, "
                + "trading_name=?, "
                + "cnpj=?, "
                + "fk_adress=?, "
                + "email=?, "
                + "date_hour_alteration=?, "
                + "fk_user_who_altered=? "
                + "WHERE pk_publisher=?");
        stm.setString(1, publisher.getCompanyName());
        stm.setString(2, publisher.getTradingName());
        stm.setString(3, publisher.getCnpj());
        stm.setInt(4, publisher.getAdress().getId());
        stm.setString(5, publisher.getEmail());
        stm.setTimestamp(6, DBConfig.now(), DBConfig.tzUTC);
        stm.setInt(7, DBConfig.idUserLogged);
        stm.setInt(8, publisher.getId());
        stm.execute();
        stm.close();
    }
    
    public static void updateExcluded(Publisher publisher) throws SQLException {
        if (publisher.getId() == 0) {
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada!");
        }
        AdressDAO.updateExcluded(publisher.getAdress());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("UPDATE publishers SET "
                + "excluded=?, "
                + "date_hour_deletion=?, "
                + "fk_user_who_deleted=?, "
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
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("DELETE FROM publishers WHERE pk_publisher=?");
        stm.setInt(1, publisher.getId());
        stm.execute();
        stm.close();
    }

}
