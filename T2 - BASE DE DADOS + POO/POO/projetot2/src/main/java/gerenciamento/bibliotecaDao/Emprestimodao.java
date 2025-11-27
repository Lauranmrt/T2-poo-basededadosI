package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Funcionario;
import gerenciamento.biblioteca.logica.tratamentodeerros.Sessao;
import java.sql.*;

public class Emprestimodao {

    private void corrigirAuditoria(Connection conn) {
        try {
            // descobre quem está logado
            Funcionario f = Sessao.getFuncionarioLogado();
            String loginCorreto = (f != null) ? f.getLogin() : "Sistema/Admin";

            // atualiza o últ. registro inserido na auditoria
            String sql = "UPDATE log_auditoria SET usuario_nome = ? ORDER BY id DESC LIMIT 1";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, loginCorreto);
                int linhasAfetadas = stmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    System.out.println("Auditoria corrigida para: " + loginCorreto);
                } else {
                    System.out.println("AVISO: Nenhuma linha de auditoria encontrada para corrigir.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao corrigir auditoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String registrarEmprestimo(int idUsuario, int idObra) throws SQLException {
        try (Connection conn = new ConexaoBanco().getConexao()) {
            
            // executa a Procedure
            String sql = "{CALL sp_registrar_emprestimo(?, ?)}";
            String msg = "Empréstimo processado.";
            
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setInt(1, idUsuario);
                stmt.setInt(2, idObra);
                boolean hasResults = stmt.execute();
                if (hasResults) {
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) msg = rs.getString("Mensagem");
                }
            }

            // troca 'root' pelo nome
            corrigirAuditoria(conn);
            
            return msg;
        }
    }

    public String registrarDevolucao(int idEmprestimo) throws SQLException {
        try (Connection conn = new ConexaoBanco().getConexao()) {
            
            String sql = "{CALL sp_registrar_devolucao(?)}";
            String msg = "Devolução processada.";
            
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setInt(1, idEmprestimo);
                boolean hasResults = stmt.execute();
                if (hasResults) {
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) msg = rs.getString("Mensagem");
                }
            }

            corrigirAuditoria(conn);
            
            return msg;
        }
    }

    public void atualizarDatas(int idEmprestimo, String novaDataPrevista) throws SQLException {
        try (Connection conn = new ConexaoBanco().getConexao()) {
            
            String sql = "UPDATE emprestimo SET data_previsao_devolucao = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, novaDataPrevista);
                stmt.setInt(2, idEmprestimo);
                stmt.executeUpdate();
            }

            corrigirAuditoria(conn);
        }
    }

    public void deletar(int id) throws SQLException {
        try (Connection conn = new ConexaoBanco().getConexao()) {
            
            String sql = "DELETE FROM emprestimo WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            corrigirAuditoria(conn);
        }
    }
}