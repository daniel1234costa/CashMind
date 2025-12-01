package views;

import java.util.List;
import java.util.Scanner;
import model.Categoria;

public class TelaCategoria {

    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        while (true) {
            System.out.println("\n=== CATEGORIAS ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Editar");
            System.out.println("4. Desativar");
            System.out.println("5. Visualizar");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            if (opcao == 0) break;

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: editar(); break;
                case 4: desativar(); break;
                case 5: visualizar(); break;
                default: System.out.println("Inválido!");
            }
        }
    }

    private void cadastrar() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        Categoria.cadastrarCategoria(nome);
    }

    private void listar() {
        System.out.println("\n--- LISTA ---");
        List<Categoria> lista = Categoria.listarCategorias();
        for (Categoria c : lista) {
            System.out.println(c.getIdCategoria() + " | " + c.getNomeCategoria() + " | " + c.getStatusDescricao());
        }
    }

    private void editar() {
        System.out.print("ID ou Nome para buscar: ");
        String termo = scanner.nextLine();
        
        Categoria cat = Categoria.buscarCategoria(termo);
        
        if (cat != null) {
            System.out.print("Novo Nome: ");
            String novoNome = scanner.nextLine();
            cat.editarCategoria(novoNome); // Chama método de instância
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }

    private void desativar() {
        System.out.print("ID ou Nome para desativar: ");
        String termo = scanner.nextLine();
        
        Categoria cat = Categoria.buscarCategoria(termo);
        
        if (cat != null) {
            if(cat.desativarCategoria()) {
                System.out.println("Categoria desativada.");
            }
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }

    private void visualizar() {
        System.out.print("ID ou Nome: ");
        String termo = scanner.nextLine();
        
        Categoria cat = Categoria.buscarCategoria(termo);
        if (cat != null) {
            cat.visualizarCategoria();
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }
}