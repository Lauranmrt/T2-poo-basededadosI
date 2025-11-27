package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Genero;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Generodao {

    public void salvar(Genero genero) throws SQLException {
        String sql = "INSERT INTO genero (nome_genero) VALUES (?)";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genero.getNome_genero().toUpperCase());
            stmt.executeUpdate();
        }
    }

    public List<Genero> listarTodos() throws SQLException {
        List<Genero> lista = new ArrayList<>();
        String sql = "SELECT * FROM genero ORDER BY nome_genero";
        try (Connection conn = new ConexaoBanco().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Genero g = new Genero(rs.getString("nome_genero"));
                g.setId(rs.getInt("id"));
                lista.add(g);
            }
        }
        return lista;
    }

    public void atualizar(Genero genero) throws SQLException {
        String sql = "UPDATE genero SET nome_genero = ? WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genero.getNome_genero().toUpperCase());
            stmt.setInt(2, genero.getId());
            stmt.executeUpdate();
        }
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM genero WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}