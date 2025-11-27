package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    // soma apenas livros disp no estoque
    public int contarLivrosDisponiveis() {
        // usa a view
        String sql = "SELECT SUM(quantidade_disponivel) FROM vw_estoque_consolidado";
        return executarQueryUnica(sql);
    }

    public int contarUsuarios() {
        return executarQueryUnica("SELECT COUNT(*) FROM usuario");
    }

    public int contarEmprestimosAtivos() {
        return executarQueryUnica("SELECT COUNT(*) FROM emprestimo WHERE status_emprestimo = 'PENDENTE'");
    }

    public int contarAtrasados() {
        return executarQueryUnica("SELECT COUNT(*) FROM emprestimo WHERE status_emprestimo = 'PENDENTE' AND data_previsao_devolucao < CURDATE()");
    }

    private int executarQueryUnica(String sql) {
        try (Connection conn = new ConexaoBanco().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<Object[]> obterAuditoriaRecente() {
        List<Object[]> logs = new ArrayList<>();
        // coloca o log mais novo primeiro
        String sql = "SELECT data_hora, acao, usuario_nome, detalhes FROM log_auditoria ORDER BY data_hora DESC LIMIT 50";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                logs.add(new Object[]{
                    rs.getTimestamp("data_hora"),
                    rs.getString("acao"),
                    rs.getString("usuario_nome"),
                    rs.getString("detalhes")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return logs;
    }
}