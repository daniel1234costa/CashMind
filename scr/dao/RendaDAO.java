package dao; // Ajustado para a pasta 'dao'

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.DatabaseConnector;
import model.Renda; // Import necessário pois Renda está em 'model'
import model.UtilData;

public class RendaDAO {

    public Renda cadastrarRenda(Renda renda) {
        if (renda.getIdRenda() == null) {
            renda.setIdRenda(UUID.randomUUID().toString());
        }
        
        // Agora usa o ID do usuário que veio no objeto Renda
        String sql = "INSERT INTO renda (id, nome, valor, data, tipo, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, renda.getIdRenda());
            stmt.setString(2, renda.getNomeRenda());
            stmt.setDouble(3, renda.getValor());
            stmt.setString(4, UtilData.formatarData(renda.getData()));
            stmt.setBoolean(5, renda.isTipoRenda());
            stmt.setString(6, renda.getIdUsuario()); // Pega do objeto

            stmt.execute();
            System.out.println("✅ Renda cadastrada! ID: " + renda.getIdRenda());

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return null;
        }
        return renda;
    }

    public boolean excluirRenda(Renda renda) {
        String sql = "DELETE FROM renda WHERE id = ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, renda.getIdRenda());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }

    public void editarRenda(String id, String nome, double valor) {
        String sql = "UPDATE renda SET nome = ?, valor = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, valor);
            stmt.setString(3, id);

            if (stmt.executeUpdate() > 0) System.out.println("✅ Renda atualizada!");
            else System.out.println("⚠️ ID não encontrado.");

        } catch (SQLException e) { System.out.println("Erro editar: " + e.getMessage()); }
    }

    public void visualizarRenda(String id) {
        String sql = "SELECT * FROM renda WHERE id = ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("--- DETALHES ---");
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Valor: R$ " + rs.getDouble("valor"));
                System.out.println("Data: " + rs.getString("data"));
                System.out.println("Tipo: " + (rs.getBoolean("tipo") ? "Fixa" : "Extra"));
            } else {
                System.out.println("Renda não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar: " + e.getMessage());
        }
    }

    // Agora recebe o ID do usuário para filtrar
    public List<Renda> listarRendasExtras(String idUsuario) {
        return listarGenerico(0, idUsuario); 
    }

    public List<Renda> listarRendasFixas(String idUsuario) {
        return listarGenerico(1, idUsuario); 
    }

    private List<Renda> listarGenerico(int tipo, String idUsuario) {
        List<Renda> lista = new ArrayList<>();
        // FILTRO ADICIONADO: AND id_usuario = ?
        String sql = "SELECT * FROM renda WHERE tipo = ? AND id_usuario = ?";
        
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tipo);
            stmt.setString(2, idUsuario); // Passa o ID do usuário
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Renda r = new Renda();
                r.setIdRenda(rs.getString("id"));
                r.setNomeRenda(rs.getString("nome"));
                r.setValor(rs.getDouble("valor"));
                r.setData(UtilData.parseData(rs.getString("data")));
                r.setTipoRenda(rs.getBoolean("tipo"));
                r.setIdUsuario(rs.getString("id_usuario"));
                lista.add(r);
            }
        } catch (SQLException e) { System.out.println("Erro listar: " + e.getMessage()); }
        return lista;
    }

    // Também precisa filtrar o total por usuário
    public double calcularRendaTotalMensal(int mes, int ano, String idUsuario) {
        double total = 0;
        // FILTRO ADICIONADO: AND id_usuario = ?
        String sql = "SELECT SUM(valor) as total FROM renda WHERE substr(data, 4, 2) = ? AND substr(data, 7, 4) = ? AND id_usuario = ?";
        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, String.format("%02d", mes));
            stmt.setString(2, String.valueOf(ano));
            stmt.setString(3, idUsuario); // Passa o ID
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) total = rs.getDouble("total");
        } catch (SQLException e) { System.out.println("Erro calc: " + e.getMessage()); }
        return total;
    }
}