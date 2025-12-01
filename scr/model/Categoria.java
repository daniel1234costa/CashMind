package model;

import java.util.List;
import dao.CategoriaDAO;

public class Categoria {
    private String idCategoria;
    private String nomeCategoria;
    private boolean status;

    public Categoria() {}

    public Categoria(String nomeCategoria, boolean status) {
        this.nomeCategoria = nomeCategoria;
        this.status = status;
    }

    // --- MÉTODOS ESTÁTICOS ---

    public static Categoria cadastrarCategoria(String nome) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Nome obrigatório.");
            return null;
        }
        Categoria nova = new Categoria(nome, true);
        new CategoriaDAO().cadastrarCategoria(nova);
        return nova;
    }

    public static List<Categoria> listarCategorias() {
        return new CategoriaDAO().listarCategorias();
    }

    public static Categoria buscarCategoria(String termo) {
        return new CategoriaDAO().buscarCategoria(termo);
    }

    // --- MÉTODOS DE INSTÂNCIA ---

    public void editarCategoria(String novoNome) {
        if (novoNome == null || novoNome.isEmpty()) return;
        new CategoriaDAO().editarCategoria(this.idCategoria, novoNome);
        this.nomeCategoria = novoNome; // Atualiza localmente também
    }

    public boolean desativarCategoria() {
        boolean sucesso = new CategoriaDAO().desativarCategoria(this.idCategoria);
        if (sucesso) this.status = false;
        return sucesso;
    }

    public void visualizarCategoria() {
        new CategoriaDAO().visualizarCategoria(this.idCategoria);
    }

    // Getters e Setters
    public String getIdCategoria() { return idCategoria; }
    public void setIdCategoria(String idCategoria) { this.idCategoria = idCategoria; }
    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    
    public String getStatusDescricao() { return status ? "Ativa" : "Desativada"; }
}