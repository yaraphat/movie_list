package main.java.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

}
