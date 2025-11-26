package model; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    // Define o caminho para o arquivo do banco de dados. 
    private static final String URL = "jdbc:sqlite:database/financas.db"; 
    
    // O driver é carregado automaticamente na maioria das versões recentes do Java (JDBC).
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver JDBC: " + e.getMessage());
        }
    }

    /**
     * Abre uma nova conexão com o banco de dados.
     * @return Objeto Connection, ou null se houver erro.
     */
    public static Connection conectar() {
        Connection conexao = null;
        try {
            // Se o arquivo 'financas.db' não existir, o SQLite o cria automaticamente.
            conexao = DriverManager.getConnection(URL);
            // Ativa o suporte a chaves estrangeiras (FOREIGN KEY) na sessão
            conexao.createStatement().execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    /**
     * Fecha uma conexão.
     * @param conn A conexão a ser fechada.
     */
    public static void fecharConexao(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}