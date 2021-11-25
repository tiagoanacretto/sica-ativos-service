package br.com.sica.sicaativosservice.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FormatUtils {

    static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    static final String DATE_FORMAT = "dd/MM/yyyy";
    static final String NAO_DISPONIVEL = "N/D";

    private FormatUtils() {}

    public static String dateTimeToString(DateTime data) {
        if (data == null) {
            return NAO_DISPONIVEL;
        } else {
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
            return dtfOut.print(data);
        }
    }

    public static String localDateToString(LocalDate data) {
        if (data == null) {
            return NAO_DISPONIVEL;
        } else {
            return data.toString(DATE_FORMAT);
        }
    }

    public static LocalDate stringToLocalDate(String localDate) {
        if (localDate != null && !NAO_DISPONIVEL.equals(localDate)) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_FORMAT);
            return dtf.parseLocalDate(localDate);
        } else {
            return null;
        }
    }

    public static DateTime stringToDateTime(String data) {
        if (data != null && !NAO_DISPONIVEL.equals(data)) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
            return dtf.parseDateTime(data);
        } else {
            return null;
        }
    }

}
