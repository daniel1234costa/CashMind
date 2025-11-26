package database;

import controller.UsuarioController;
import java.util.Date;
import model.Usuario; 

public class TesteGeral {
    
    public static void main(String[] args) {
        
        System.out.println("--- INICIANDO TESTE GERAL DE PERSISTÊNCIA ---");
        
        UsuarioController controller = new UsuarioController();

        // Dados de teste para registro
        String nome = "João da Silva";
        String email = "joao.silva@teste.com";
        String senha = "senha123";
        Date hoje = new Date(); 

        // 1. Tenta REGISTRAR (INSERT) um novo usuário
        boolean registrado = controller.registrarNovoUsuario(
            nome, 
            email, 
            senha, 
            hoje
        );
        
        System.out.println("\n1. Teste de Registro (INSERT): " + (registrado ? "✅ Sucesso! Dados salvos no DB." : "❌ Falhou! (Email pode já existir)"));

        // --- Verificação de Persistência ---
        
        // 2. Tenta fazer LOGIN (SELECT) com as credenciais salvas
        Usuario usuarioLogado = controller.login(email, senha);
        
        if (usuarioLogado != null) {
            System.out.println("\n2. Teste de Login (SELECT e HASH): ✅ Sucesso!");
            System.out.println("   ID Recuperado do DB: " + usuarioLogado.getIdUsuario());
            System.out.println("   Nome: " + usuarioLogado.getNome());
            // Lembre-se, o Controller setou a senha como null antes de retornar.
        } else {
            System.out.println("\n2. Teste de Login (SELECT e HASH): ❌ Falhou! Senha ou email incorretos.");
        }
        
        // 3. Tenta fazer LOGIN com senha errada (Teste de Hashing)
        Usuario falha = controller.login(email, "senhaErrada");
        System.out.println("\n3. Teste de Segurança (Senha Errada): " + (falha == null ? "✅ OK! Login negado." : "❌ Falha! Logou com senha errada."));
        
        System.out.println("\n--- FIM DOS TESTES ---");
    }
}