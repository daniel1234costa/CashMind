package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Categoria;
import model.DatabaseConnector;

public class CategoriaDAO {

    public void cadastrarCategoria(Categoria categoria) {
        String sql = "INSERT INTO Categoria (idCategoria, nomeCategoria, status) VALUES (?, ?, 1)";

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gera UUID se não tiver
            if (categoria.getIdCategoria() == null) {
                categoria.setIdCategoria(UUID.randomUUID().toString());
            }

            stmt.setString(1, categoria.getIdCategoria());
            stmt.setString(2, categoria.getNomeCategoria());
            
            stmt.execute();
            System.out.println("✅ Categoria cadastrada!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public void editarCategoria(String id, String novoNome) {
        String sql = "UPDATE Categoria SET nomeCategoria = ? WHERE idCategoria = ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setString(2, id);

            if (stmt.executeUpdate() > 0) System.out.println("✅ Categoria atualizada!");
            else System.out.println("⚠️ ID não encontrado.");

        } catch (SQLException e) {
            System.out.println("Erro ao editar: " + e.getMessage());
        }
    }

    public boolean desativarCategoria(String id) {
        String sql = "UPDATE Categoria SET status = 0 WHERE idCategoria = ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao desativar: " + e.getMessage());
            return false;
        }
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getString("idCategoria"));
                c.setNomeCategoria(rs.getString("nomeCategoria"));
                c.setStatus(rs.getBoolean("status"));
                lista.add(c);
            }
        } catch (SQLException e) { System.out.println("Erro listar: " + e.getMessage()); }
        return lista;
    }

    public Categoria buscarCategoria(String nomeOrId) {
        // Busca por ID ou Nome (LIKE)
        String sql = "SELECT * FROM Categoria WHERE idCategoria = ? OR nomeCategoria LIKE ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeOrId);
            stmt.setString(2, "%" + nomeOrId + "%");
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getString("idCategoria"));
                c.setNomeCategoria(rs.getString("nomeCategoria"));
                c.setStatus(rs.getBoolean("status"));
                return c;
            }
        } catch (SQLException e) { System.out.println("Erro buscar: " + e.getMessage()); }
        return null;
    }
    
    public void visualizarCategoria(String id) {
        Categoria c = buscarCategoria(id);
        if (c != null) {
            System.out.println("--- CATEGORIA ---");
            System.out.println("ID: " + c.getIdCategoria());
            System.out.println("Nome: " + c.getNomeCategoria());
            System.out.println("Status: " + (c.isStatus() ? "Ativa" : "Inativa"));
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }
}