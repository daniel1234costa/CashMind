package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    // Caminho do banco
    private static final String URL = "jdbc:sqlite:database/financas.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro driver: " + e.getMessage());
        }
    }

    public static Connection conectar() {
        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(URL);

            try (Statement stmt = conexao.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            criarTabelas(conexao);

        } catch (SQLException e) {
            System.err.println("Erro conexão: " + e.getMessage());
        }

        return conexao;
    }

    private static void criarTabelas(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Usuario (
                    id_usuario VARCHAR(255) PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL,
                    senha VARCHAR(255) NOT NULL,
                    data_nascimento DATE
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Categoria (
                    idCategoria VARCHAR(255) PRIMARY KEY,
                    nomeCategoria VARCHAR(255) NOT NULL,
                    status BOOLEAN NOT NULL DEFAULT TRUE,
                    idUsuario VARCHAR(255) NOT NULL,
                    FOREIGN KEY (idUsuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Renda (
                    id TEXT PRIMARY KEY,
                    idUsuario VARCHAR(255) NOT NULL,
                    nome TEXT NOT NULL,
                    valor REAL NOT NULL,
                    data TEXT NOT NULL,
                    tipo BOOLEAN NOT NULL,
                    FOREIGN KEY (idUsuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Despesa (
                    idDespesa VARCHAR(255) PRIMARY KEY,
                    idUsuario VARCHAR(255) NOT NULL,
                    idCategoria VARCHAR(255) NOT NULL,
                    nomeDespesa VARCHAR(255) NOT NULL,
                    valor NUMERIC(10, 2) NOT NULL,
                    data DATE NOT NULL,
                    FOREIGN KEY (idUsuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
                    FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria) ON DELETE RESTRICT
                );
            """);

            stmt.execute("""
                INSERT OR IGNORE INTO Usuario (id_usuario, nome, email, senha)
                VALUES ('1', 'Admin', 'admin', '123');
            """);

        } catch (SQLException e) {
            System.err.println("Erro tabelas: " + e.getMessage());
        }
    }

    public static void fecharConexao(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {}
    }
}