package model;

import java.util.List;

import dao.CategoriaDAO;

public class Categoria {
    private String idCategoria;
    private String nomeCategoria;
    private boolean status;

    public Categoria() {}

    public Categoria(String idCategoria, String nomeCategoria, boolean status) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.status = status;
    }

    // --- MÉTODOS ESTÁTICOS (Model chama DAO) ---

    public static void cadastrarCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            System.out.println("Erro: O nome da categoria é obrigatório.");
            return;
        }
        CategoriaDAO dao = new CategoriaDAO();
        dao.cadastrarCategoria(nomeCategoria);
    }

    public static void editarCategoria(String id, String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            System.out.println("Erro: O nome da categoria é obrigatório.");
            return;
        }
        CategoriaDAO dao = new CategoriaDAO();
        dao.editarCategoria(id, novoNome);
    }

    public static void desativarCategoria(String id) {
        CategoriaDAO dao = new CategoriaDAO();
        dao.desativarCategoria(id);
    }

    public static List<Categoria> listarCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        return dao.listarCategorias();
    }

    public static Categoria buscarCategoria(String id) {
        CategoriaDAO dao = new CategoriaDAO();
        return dao.buscarCategoria(id);
    }

    // --- MÉTODOS DE INSTÂNCIA ---

    public void visualizarCategoria() {
        CategoriaDAO dao = new CategoriaDAO();
        dao.visualizarCategoria(this.idCategoria);
    }

    // --- GETTERS E SETTERS ---
    public String getIdCategoria() { return idCategoria; }
    public void setIdCategoria(String idCategoria) { this.idCategoria = idCategoria; }
    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    
    // Método auxiliar para facilitar a exibição
    public String getStatusDescricao() {
        return status ? "Ativo" : "Desativado";
    }
}