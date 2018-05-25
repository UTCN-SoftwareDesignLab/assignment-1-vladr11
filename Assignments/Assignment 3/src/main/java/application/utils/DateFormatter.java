package application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static Date dateFromString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date;
        try {
            date = dateFormat.parse(dateString);
            if (date == null) {
                SimpleDateFormat fallbackDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                date = fallbackDateFormatter.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String stringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(date);
    }
}
