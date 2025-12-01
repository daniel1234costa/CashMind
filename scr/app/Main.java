package app;

import java.util.Scanner;

import model.Sessao;
import views.TelaCategoria;
import views.TelaRenda;
import views.TelaUsuario;
import views.TelaDespesa;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Loop infinito principal da aplicação
        while (true) {
            
            // Verifica se existe usuário na sessão
            if (!Sessao.isLogado()) {
                exibirMenuAcesso(scanner);
            } else {
                exibirMenuPrincipal(scanner);
            }
        } 
    }

    // MENU PARA QUEM NÃO ESTÁ LOGADO
    private static void exibirMenuAcesso(Scanner scanner) {
        System.out.println("\n==========================================");
        System.out.println("     SISTEMA DE FINANÇAS PESSOAIS");
        System.out.println("==========================================");
        System.out.println("1. Login / Entrar");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.println("0. Sair do Sistema");
        System.out.println("==========================================");
        System.out.print("Escolha uma opção: ");

        int opcao = 0;
        try {
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir o \n
            } else {
                scanner.nextLine();
                System.out.println("Por favor, digite apenas números!");
                return;
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada inválida.");
            return;
        }

        switch (opcao) {
            case 1:
                TelaUsuario telaLogin = new TelaUsuario(scanner);
                String idLogado = telaLogin.exibirMenuLogin(); 
                // Se o login foi sucesso, salva na sessão
                if (idLogado != null) {
                    Sessao.logar(idLogado);
                }
                break;
            case 2:
                TelaUsuario telaCadastro = new TelaUsuario(scanner);
                telaCadastro.exibirMenuCadastro(); 
                break;
            case 0:
                System.out.println("Saindo... Até logo!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    
    // MENU PARA QUEM JÁ ESTÁ LOGADO
    private static void exibirMenuPrincipal(Scanner scanner) {
        // Exibe 8 primeiros caracteres do ID apenas para debug visual
        String idDisplay = (Sessao.getIdUsuarioLogado().length() > 8) 
            ? Sessao.getIdUsuarioLogado().substring(0, 8) + "..." 
            : Sessao.getIdUsuarioLogado();

        System.out.println("\n==========================================");
        System.out.println("     MENU PRINCIPAL (ID: " + idDisplay + ")");
        System.out.println("==========================================");
        System.out.println("1. Módulo de Rendas ");
        System.out.println("2. Módulo de Despesas ");
        System.out.println("3. Módulo de Categorias ");
        System.out.println("4. Meu Perfil (Visualizar / Editar / Excluir) "); // Nova opção
        System.out.println("9. Logout (Trocar Usuário)");
        System.out.println("0. Sair do Sistema");
        System.out.println("==========================================");
        System.out.print("Escolha uma opção: ");

        int opcao = 0;
        try {
             if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Por favor, digite apenas números!");
                return;
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada inválida.");
            return;
        }
        
        switch (opcao) {
            case 1:
                TelaRenda telaRenda = new TelaRenda(); 
                telaRenda.exibirMenu(); 
                break;
            
            case 2:
                TelaDespesa telaDespesa = new TelaDespesa();
                telaDespesa.exibirMenu();
                break;

            case 3:
                TelaCategoria telaCategoria = new TelaCategoria(); 
                telaCategoria.exibirMenu();
                break;
            
            // --- NOVO SUB-MENU DE PERFIL ---
            case 4: 
                TelaUsuario telaUsuario = new TelaUsuario(scanner);
                telaUsuario.exibirMenuPerfil();
                break;
                
            case 9:
                Sessao.deslogar();
                System.out.println("Logout realizado. Retornando à tela de acesso.");
                break;

            case 0:
                System.out.println("Saindo... Até logo!");
                System.exit(0);
                break;

            default:
                System.out.println(" Opção inválida!");
        }
    }
}