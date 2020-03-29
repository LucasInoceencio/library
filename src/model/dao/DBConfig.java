package model.dao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

public class DBConfig {
    
    public static final String URL = "jdbc:postgresql://localhost:5432/library";
    public static final String USER = "postgres";
    public static final String PASSWORD = "0485";
    public static final String DRIVER = "org.postgresql.Driver";
    public static int idUserLogged = 1;

    // Configuração de data no formato UTC
    public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    
    public static Timestamp now() {
        Instant instant = Instant.now();
        Timestamp now = new Timestamp(instant.toEpochMilli());
        return now;
    }
}
