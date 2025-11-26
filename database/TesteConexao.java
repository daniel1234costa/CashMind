package database;
import java.sql.Connection;
import model.DatabaseConnector; 

public class TesteConexao {
    
    public static void main(String[] args) {
        

        Connection conn = DatabaseConnector.conectar(); 
        
        if (conn != null) {
            System.out.println("✅ Conexão com o SQLite estabelecida com sucesso!");
            

            DatabaseConnector.fecharConexao(conn); 
        } else {
            System.err.println("❌ Falha na conexão. Verifique o driver e o caminho 'financas.db'.");
        }
    }
}