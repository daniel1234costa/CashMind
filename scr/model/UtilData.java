package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilData {

    // Formato usado pelo usuário (entrada de dados)
    private static final SimpleDateFormat FORMAT_USER = new SimpleDateFormat("dd/MM/yyyy");

    // Formato usado pelo SQLite (padrão no banco de dados)
    private static final SimpleDateFormat FORMAT_DB = new SimpleDateFormat("yyyy-MM-dd");

    static {
        FORMAT_USER.setLenient(false);
        FORMAT_DB.setLenient(false);
    }

    /**
     * Converte java.util.Date → String para salvar no banco (yyyy-MM-dd).
     */
    public static String formatarData(Date data) {
        if (data == null) return null;
        return FORMAT_DB.format(data);
    }

    /**
     * Converte String digitada pelo usuário (dd/MM/yyyy) → java.util.Date.
     */
    public static Date parseDataUsuario(String dataString) {
        if (dataString == null || dataString.isEmpty()) return null;

        try {
            return FORMAT_USER.parse(dataString);
        } catch (ParseException e) {
            System.err.println("Erro ao converter data do usuário: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converte String do banco (yyyy-MM-dd) → java.util.Date.
     * ESTE É O MÉTODO CORRETO PARA LEITURA DO BANCO DE DADOS.
     */
    public static Date parseDataBanco(String dataString) {
        if (dataString == null || dataString.isEmpty()) return null;

        try {
            return FORMAT_DB.parse(dataString);
        } catch (ParseException e) {
            System.err.println("Erro ao converter data do banco: " + e.getMessage());
            return null;
        }
    }
}