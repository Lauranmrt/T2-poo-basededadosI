package gerenciamento.bibliotecaDao;

import java.sql.Connection;

import gerenciamento.bibliotecadb.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Exemplardao {
    
    public void adicionarExemplar(int idObra) throws SQLException {
        String sql = "INSERT INTO exemplar (obra_id, status_livro) VALUES (?, 'DISPONIVEL')";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idObra);
            stmt.executeUpdate();
        }
    }

    public List<Object[]> listarEstoque() throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT e.id, o.titulo, e.status_livro " +
                     "FROM exemplar e JOIN obra o ON e.obra_id = o.id " +
                     "ORDER BY o.titulo";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"), rs.getString("titulo"), rs.getString("status_livro")
                });
            }
        }
        return lista;
    }
}