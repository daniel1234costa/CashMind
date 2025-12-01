package views;

import dao.CategoriaDAO;
import dao.DespesaDAO;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Categoria;
import model.Despesa;
import model.Sessao;
import model.UtilData;

public class TelaDespesa {

    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {

        while (true) {

            System.out.println("\n====== MENU DE DESPESAS ======");
            System.out.println("1 - Cadastrar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Editar Despesa");
            System.out.println("4 - Excluir Despesa");
            System.out.println("5 - Visualizar Despesa");
            System.out.println("6 - Listar por Categoria");
            System.out.println("7 - Listar por Período");
            System.out.println("8 - Buscar por Termo");
            System.out.println("9 - Total Mensal");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine().trim();

            int opcao;
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            if (opcao == 0) break;

            switch (opcao) {
                case 1 -> cadastrarDespesa();
                case 2 -> listarDespesas();
                case 3 -> editarDespesa();
                case 4 -> excluirDespesa();
                case 5 -> visualizarDespesa();
                case 6 -> listarPorCategoria();
                case 7 -> listarPorPeriodo();
                case 8 -> buscarDespesa();
                case 9 -> totalMensal();
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarDespesa() {

        System.out.print("Nome da despesa: ");
        String nome = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());

        System.out.print("Data (dd/MM/yyyy): ");
        Date data = UtilData.parseDataUsuario(scanner.nextLine());

        // ✅ Listar categorias do usuário
        List<Categoria> categorias = Categoria.listarCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Você não possui categorias cadastradas.");
            return;
        }

        System.out.println("\nCategorias disponíveis:");
        for (Categoria c : categorias) {
            System.out.println(c.getIdCategoria() + " - " + c.getNomeCategoria());
        }

        System.out.print("Digite o ID da categoria: ");
        String idCategoria = scanner.nextLine();

        Despesa despesa = new Despesa(
            Sessao.getIdUsuarioLogado(),
            nome,
            valor,
            data,
            idCategoria
        );

        if (Despesa.cadastrarDespesa(despesa)) {
            System.out.println("Despesa cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar despesa.");
        }
    }

    private void listarDespesas() {

        List<Despesa> lista = Despesa.listarDespesas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        for (Despesa d : lista) {
            d.visualizarDespesa();
        }
    }

    private void editarDespesa() {

        System.out.print("Digite o ID da despesa: ");
        String id = scanner.nextLine();

        DespesaDAO dao = new DespesaDAO();
        Despesa d = dao.buscarPorId(id, Sessao.getIdUsuarioLogado());

        if (d == null) {
            System.out.println("Despesa não encontrada ou não pertence a você.");
            return;
        }

        System.out.print("Novo nome: ");
        d.setNomeDespesa(scanner.nextLine());

        System.out.print("Novo valor: ");
        d.setValor(Double.parseDouble(scanner.nextLine()));

        System.out.print("Nova data (dd/MM/yyyy): ");
        Date data = UtilData.parseDataUsuario(scanner.nextLine());
        d.setData(data);
        
        System.out.print("Novo ID de categoria: ");
        d.setIdCategoria(scanner.nextLine());

        d.editarDespesa();
        System.out.println("Despesa atualizada!");
    }

    private void excluirDespesa() {

        System.out.print("Digite o ID da despesa: ");
        String id = scanner.nextLine();

        if (Despesa.excluirDespesa(id)) {
            System.out.println("Despesa excluída com sucesso.");
        } else {
            System.out.println("Erro ao excluir ou despesa não pertence a você.");
        }
    }

    private void visualizarDespesa() {

        System.out.print("Digite um termo para buscar a despesa: ");
        String termo = scanner.nextLine();

        Despesa d = Despesa.buscarDespesa(termo);

        if (d == null) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        if (!d.getIdUsuario().equals(Sessao.getIdUsuarioLogado())) {
            System.out.println("Acesso negado. Essa despesa não pertence a você.");
            return;
        }

        d.visualizarDespesa();
    }

    private void listarPorCategoria() {

        System.out.print("Digite o ID da categoria: ");
        String idCategoria = scanner.nextLine();

        List<Despesa> lista = Despesa.listarDespesasPorCategoria(idCategoria);

        if (lista.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada nessa categoria.");
            return;
        }

        for (Despesa d : lista) {
            d.visualizarDespesa();
        }
    }

    private void listarPorPeriodo() {

        try {
            System.out.print("Data inicial (dd/MM/yyyy): ");
            Date inicio = UtilData.parseDataUsuario(scanner.nextLine());

            System.out.print("Data final (dd/MM/yyyy): ");
            Date fim = UtilData.parseDataUsuario(scanner.nextLine());

            List<Despesa> lista = Despesa.listarDespesasPorPeriodo(inicio, fim);

            if (lista.isEmpty()) {
                System.out.println("Nenhuma despesa encontrada no período.");
                return;
            }

            for (Despesa d : lista) {
                d.visualizarDespesa();
            }

        } catch (Exception e) {
            System.out.println("Data inválida.");
        }
    }

    private void buscarDespesa() {

        System.out.print("Digite um termo para buscar: ");
        String termo = scanner.nextLine();

        Despesa d = Despesa.buscarDespesa(termo);

        if (d == null) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        d.visualizarDespesa();
    }
    private void totalMensal() {

        System.out.print("Digite o mês (1-12): ");
        int mes = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o ano: ");
        int ano = Integer.parseInt(scanner.nextLine());

        double total = Despesa.calcularDespesaTotalMensal(mes, ano);

        System.out.println("Total de despesas no período: R$ " + total);
    }
}