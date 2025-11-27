package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Editora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Editoradao {

    public void salvar(Editora editora) throws SQLException {
        String sql = "INSERT INTO editora (nome_editora) VALUES (?)";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, editora.getNome_editora().toUpperCase());
            stmt.executeUpdate();
        }
    }

    public List<Editora> listarTodas() throws SQLException {
        List<Editora> lista = new ArrayList<>();
        String sql = "SELECT * FROM editora ORDER BY nome_editora";
        try (Connection conn = new ConexaoBanco().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Editora e = new Editora(rs.getString("nome_editora"));
                e.setId(rs.getInt("id"));
                lista.add(e);
            }
        }
        return lista;
    }

    public void atualizar(Editora editora) throws SQLException {
        String sql = "UPDATE editora SET nome_editora = ? WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, editora.getNome_editora().toUpperCase());
            stmt.setInt(2, editora.getId());
            stmt.executeUpdate();
        }
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM editora WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
