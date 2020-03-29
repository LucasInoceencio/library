
package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import model.LegalPerson;
import model.dao.DBConnection;

public class LegalPersonDAO {
    
    public static int create(LegalPerson legalPerson) throws SQLException {
        Date d = new Date();
        int fkAdress = AdressDAO.create(legalPerson.getAdress());
        Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO legal_persons "
                + "(company_name, "
                + "tranding_name, "
                + "cnpj, "
                + "fk_adress, "
                + "email, "
                + "date_hour_inclusion) "
                + "VALUES(?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, legalPerson.getCompanyName());
        stm.setString(2, legalPerson.getTradingName());
        stm.setString(3, legalPerson.getCnpj());
        stm.setInt(4, fkAdress);
        stm.setString(5, legalPerson.getEmail());
        stm.setDate(6, (java.sql.Date) d);
        stm.execute();
        
        ResultSet rs = stm.getGeneratedKeys();
        rs.next();
        legalPerson.setId(rs.getInt("pk_legal_person"));
        return legalPerson.getId();
    }
}
