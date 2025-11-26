package controller;

import model.Usuario;
import model.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt; 
import java.util.Date;
import java.util.UUID; 
import java.util.List; 
import java.util.ArrayList; 

public class UsuarioController {
    
   
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ----------------------------------------------------
    // METODOS DE AUTENTICAÇÃO (JÁ EXISTENTES)
    // ----------------------------------------------------

    public boolean registrarNovoUsuario(String nome, String email, String senhaPura, Date dataNascimento) {
        
        // Gera o hash da senha
        String senhaHash = BCrypt.hashpw(senhaPura, BCrypt.gensalt());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setIdUsuario(UUID.randomUUID().toString()); 
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senhaHash); 
        novoUsuario.setDataNascimento(dataNascimento);

        return usuarioDAO.registrarUsuario(novoUsuario);
    }

    public Usuario login(String email, String senhaPura) {
        
        Usuario usuarioDB = usuarioDAO.buscarPorEmail(email);

        if (usuarioDB == null) {
            System.out.println("Erro de Login: Usuário não encontrado.");
            return null;
        }

        String hashSalvo = usuarioDB.getSenha();
        
        if (BCrypt.checkpw(senhaPura, hashSalvo)) {
            
            usuarioDB.setSenha(null); 
            System.out.println("Login bem-sucedido! Bem-vindo(a), " + usuarioDB.getNome());
            return usuarioDB;
        } else {
            System.out.println("Erro de Login: Senha incorreta.");
            return null;
        }
    }



    // Métodos finalizados registrar e login que utilizam banco de dados;
    // Editar usuario ainda apresenta problemas;
    // excluir ainda precisa ser testado.



    /**
     * Atualiza o nome, email e data de nascimento do usuário.
     * @param usuario O objeto Usuario com as informações atualizadas.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean editarUsuario(Usuario usuario) {
        // Validação básica do Controller
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().isEmpty()) {
            System.err.println("Erro: ID do usuário é necessário para a edição.");
            return false;
        }

        System.out.println("-> Tentando atualizar perfil do usuário: " + usuario.getNome());
        
        // Chama o método DAO para persistência (UPDATE)
        boolean sucesso = usuarioDAO.atualizarUsuario(usuario);

        if (sucesso) {
            System.out.println("✅ Perfil atualizado com sucesso!");
        } else {
            System.err.println("❌ Falha ao atualizar o perfil. Verifique o ID e o email.");
        }
        return sucesso;
    }

    /**
     * Remove o usuário do banco de dados permanentemente.
     * @param idUsuario O ID do usuário a ser excluído.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     */
    public boolean excluirUsuario(String idUsuario) {
        // Validação básica do Controller
        if (idUsuario == null || idUsuario.isEmpty()) {
            System.err.println("Erro: ID do usuário é necessário para a exclusão.");
            return false;
        }

        System.out.println("-> Excluindo usuário (ID: " + idUsuario + ")");
        
        // Chama o método DAO para persistência (DELETE)
        boolean sucesso = usuarioDAO.excluirUsuario(idUsuario);

        if (sucesso) {
            System.out.println("✅ Usuário excluído permanentemente.");
        } else {
            System.err.println("❌ Falha ao excluir o usuário. ID não encontrado.");
        }
        return sucesso;
    }

    /**
     * Lista todos os usuários cadastrados.
     * @return Uma lista de objetos Usuario.
     */
    public List<Usuario> listarTodosUsuarios() {
        System.out.println("-> Buscando todos os usuários no sistema...");
        return usuarioDAO.listarTodosUsuarios(); // Chama o método DAO
    }
}