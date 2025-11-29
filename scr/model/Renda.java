package model;

import java.util.Date;
import java.util.List;

public class Renda {
    private String idRenda;
    private String nomeRenda;
    private double valor;
    private Date data;
    private boolean tipoRenda;
    private String idUsuario; // Necessário para o banco

    // Construtor Vazio
    public Renda() {}

    // Construtor Cheio (Para criar nova)
    public Renda(String nomeRenda, double valor, Date data, boolean tipoRenda) {
        this.nomeRenda = nomeRenda;
        this.valor = valor;
        this.data = data;
        this.tipoRenda = tipoRenda;
    }

    // --- MÉTODOS ESTÁTICOS (Sublinhados no Diagrama) ---
    // Estes métodos pertencem à CLASSE, não precisam de um objeto criado para chamar.

    public static Renda cadastrarRenda(String nome, double valor, Date data, boolean tipoRenda) {
        RendaDAO dao = new RendaDAO();
        // O DAO vai tratar de pegar o ID do usuário (da Sessão ou fixo)
        return dao.cadastrarRenda(nome, valor, data, tipoRenda);
    }

    public static boolean excluirRenda(Renda renda) {
        RendaDAO dao = new RendaDAO();
        return dao.excluirRenda(renda);
    }

    public static List<Renda> listarRendasExtras() {
        RendaDAO dao = new RendaDAO();
        return dao.listarRendasExtras();
    }

    public static List<Renda> listarRendasFixas() {
        RendaDAO dao = new RendaDAO();
        return dao.listarRendasFixas();
    }

    public static double calcularRendaTotalMensal(int mes, int ano) {
        RendaDAO dao = new RendaDAO();
        return dao.calcularRendaTotalMensal(mes, ano);
    }

    // --- MÉTODOS DE INSTÂNCIA (Não sublinhados) ---
    // Estes métodos usam os dados do próprio objeto (this).

    public void editarRenda(String id, String nome, double valor) {
        RendaDAO dao = new RendaDAO();
        dao.editarRenda(id, nome, valor);
    }

    public void visualizarRenda() {
        RendaDAO dao = new RendaDAO();
        // Usa o ID deste objeto para buscar os detalhes
        dao.visualizarRenda(this.idRenda);
    }

    // --- GETTERS E SETTERS ---
    public String getIdRenda() 
    { return idRenda;

     };

    public void setIdRenda(String idRenda) { this.idRenda = idRenda; }
    public String getNomeRenda() { return nomeRenda; }
    public void setNomeRenda(String nomeRenda) { this.nomeRenda = nomeRenda; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public boolean isTipoRenda() { return tipoRenda; }
    public void setTipoRenda(boolean tipoRenda) { this.tipoRenda = tipoRenda; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
}