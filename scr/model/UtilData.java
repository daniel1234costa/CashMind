package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilData {
    // Formato padrão ISO 8601 (YYYY-MM-DD) para comunicação com o SQLite
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatarData(Date data) {
        if (data == null) {
            return null;
        }
        return FORMATTER.format(data);
    }

    public static Date parseData(String dataString) {
        if (dataString == null || dataString.isEmpty()) {
            return null;
        }
        try {
            return FORMATTER.parse(dataString);
        } catch (ParseException e) {
            System.err.println("Erro ao converter String para Date: " + e.getMessage());
            return null;
        }
    }
}