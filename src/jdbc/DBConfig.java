package jdbc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;
import model.User;

public class DBConfig {
    
    public static final String URL = "jdbc:postgresql://localhost:5432/library";
    public static final String USER = "postgres";
    public static final String PASSWORD = "0485";
    public static final String DRIVER = "org.postgresql.Driver";
    public static User userLogged;

    // Configuração de data no formato UTC
    public static final Calendar TZ_UTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    
    public static Timestamp now() {
        Instant instant = Instant.now();
        Timestamp now = new Timestamp(instant.toEpochMilli());
        return now;
    }
}
