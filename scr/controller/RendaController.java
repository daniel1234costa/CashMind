package controller;

import java.util.Date;
import java.util.List;
import model.Renda;

public class RendaController {

    public void cadastrarRenda(String nome, double valor, Date data, boolean tipo) {
        if (valor <= 0) {
            System.out.println("Erro: Valor inválido.");
            return;
        }
        // Chama o método ESTÁTICO da classe Renda
        Renda.cadastrarRenda(nome, valor, data, tipo);
    }

    public void excluirRenda(String id) {

        Renda rendaParaExcluir = new Renda();
        rendaParaExcluir.setIdRenda(id);

        boolean sucesso = Renda.excluirRenda(rendaParaExcluir);
        
        if (sucesso) {
            System.out.println("Renda excluída com sucesso!");
        } else {
            System.out.println("Erro: Não foi possível excluir.");
        }
    }

    public void editarRenda(String id, String nome, double valor) {
        // Cria objeto temporário para chamar método de instância
        Renda r = new Renda();
        
        r.editarRenda(id, nome, valor);
    }

    public void visualizarRenda(String id) {
        Renda r = new Renda();
        r.setIdRenda(id);
        r.visualizarRenda();
    }

    public List<Renda> listarExtras() {
        return Renda.listarRendasExtras();
    }

    public List<Renda> listarFixas() {
        return Renda.listarRendasFixas();
    }
    
    public double totalMensal(int mes, int ano) {
        return Renda.calcularRendaTotalMensal(mes, ano);
    }
}