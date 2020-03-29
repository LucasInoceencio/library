package application;

import controller.dao.AdressDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import model.Adress;

public class Program {

    public static void main(String[] args) throws SQLException {

        Adress a = new Adress("Av das Nações", 525, "Itaguai I", "Casa Verde Água", "75690000", "Caldas Novas", "Goiás");
        AdressDAO.create(a);
    }
}
