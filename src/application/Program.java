package application;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Program {

    public static void main(String[] args) throws SQLException, ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

        int deliveryTime = 72;
        Date currentDate = Date.from(Instant.parse("2020-04-11T20:17:07Z"));
        Calendar auxCal = Calendar.getInstance();
        auxCal.setTime(currentDate);
        auxCal.add(Calendar.HOUR_OF_DAY, deliveryTime);
        Date auxDeliveryDate;
        switch (auxCal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                auxCal.add(Calendar.HOUR_OF_DAY, 24);
                auxDeliveryDate = auxCal.getTime();
                break;
            case 7:
                auxCal.add(Calendar.HOUR_OF_DAY, 48);
                auxDeliveryDate = auxCal.getTime();
                break;
            
            default:
                auxDeliveryDate = auxCal.getTime();
                break;
        }

        System.out.println("Date: " + currentDate);
        System.out.println("Calendar: " + auxCal);
        System.out.println("New Date: " + auxDeliveryDate);
        System.out.println(auxCal.DAY_OF_WEEK);
    }
}
