package dao;

import java.sql.*;
import java.util.*;
import model.DatabaseConnector;
import model.Renda;
import model.UtilData;

public class RendaDAO {

    public Renda cadastrarRenda(Renda renda) {

        if (renda.getIdRenda() == null) {
            renda.setIdRenda(UUID.randomUUID().toString());
        }

        String sql = "INSERT INTO Renda (id, idUsuario, nome, valor, data, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, renda.getIdRenda());
            stmt.setString(2, renda.getIdUsuario());
            stmt.setString(3, renda.getNomeRenda());
            stmt.setDouble(4, renda.getValor());
            stmt.setString(5, UtilData.formatarData(renda.getData()));
            stmt.setBoolean(6, renda.isTipoRenda());

            stmt.executeUpdate();
            return renda;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar renda: " + e.getMessage());
            return null;
        }
    }

    public boolean excluirRenda(Renda renda) {

        String sql = "DELETE FROM Renda WHERE id = ? AND idUsuario = ?";

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, renda.getIdRenda());
            stmt.setString(2, renda.getIdUsuario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir renda: " + e.getMessage());
            return false;
        }
    }

    public void editarRenda(String id, String nome, double valor) {

        String sql = "UPDATE Renda SET nome = ?, valor = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, valor);
            stmt.setString(3, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao editar renda: " + e.getMessage());
        }
    }

    public void visualizarRenda(String id) {

        String sql = "SELECT * FROM Renda WHERE id = ?";

        try (Connection conn = DatabaseConnector.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- DETALHES DA RENDA ---");
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Valor: R$ " + rs.getDouble("valor"));
                System.out.println("Data: " + rs.getString("data"));
                System.out.println("Tipo: " + (rs.getBoolean("tipo") ? "Fixa" : "Extra"));
                System.out.println("---------------------------");
            } else {
                System.out.println("Renda não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao visualizar renda: " + e.getMessage());
        }
    }

    public Renda buscarPorId(String id, String idUsuario) {

        String sql = "SELECT * FROM Renda WHERE id = ? AND idUsuario = ?";

        try (Connection conn = DatabaseConnector.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, idUsuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Renda r = new Renda();
                r.setIdRenda(rs.getString("id"));
                r.setIdUsuario(rs.getString("idUsuario"));
                r.setNomeRenda(rs.getString("nome"));
                r.setValor(rs.getDouble("valor"));
                r.setData(UtilData.parseDataBanco(rs.getString("data")));
                r.setTipoRenda(rs.getBoolean("tipo"));
                return r;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar renda: " + e.getMessage());
        }

        return null;
    }

    public List<Renda> listarRendasExtras(String idUsuario) {
        return listarPorTipo(false, idUsuario);
    }

    public List<Renda> listarRendasFixas(String idUsuario) {
        return listarPorTipo(true, idUsuario);
    }

    private List<Renda> listarPorTipo(boolean tipo, String idUsuario) {

        List<Renda> lista = new ArrayList<>();

        String sql = "SELECT * FROM Renda WHERE tipo = ? AND idUsuario = ? ORDER BY date(data) DESC";

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, tipo);
            stmt.setString(2, idUsuario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Renda r = new Renda();
                r.setIdRenda(rs.getString("id"));
                r.setIdUsuario(rs.getString("idUsuario"));
                r.setNomeRenda(rs.getString("nome"));
                r.setValor(rs.getDouble("valor"));
                r.setData(UtilData.parseDataBanco(rs.getString("data")));
                r.setTipoRenda(rs.getBoolean("tipo"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar rendas: " + e.getMessage());
        }

        return lista;
    }

    public double calcularRendaTotalMensal(int mes, int ano, String idUsuario) {

        String sql = """
            SELECT SUM(valor) AS total
            FROM Renda
            WHERE strftime('%m', data) = ?
            AND strftime('%Y', data) = ?
            AND idUsuario = ?
        """;

        try (Connection conn = DatabaseConnector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, String.format("%02d", mes));
            stmt.setString(2, String.valueOf(ano));
            stmt.setString(3, idUsuario);

            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getDouble("total") : 0.0;

        } catch (SQLException e) {
            System.out.println("Erro ao calcular total mensal: " + e.getMessage());
            return 0.0;
        }
    }
}