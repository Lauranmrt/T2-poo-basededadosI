package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Autordao {

    public void salvar(Autor autor) throws SQLException {
        String sql = "INSERT INTO autor (nome_autor) VALUES (?)";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getNome_autor().toUpperCase());
            stmt.executeUpdate();
        }
    }

    public List<Autor> listarTodos() throws SQLException {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor ORDER BY nome_autor";
        try (Connection conn = new ConexaoBanco().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Autor a = new Autor(rs.getString("nome_autor"));
                a.setId(rs.getInt("id"));
                lista.add(a);
            }
        }
        return lista;
    }

    public void atualizar(Autor autor) throws SQLException {
        String sql = "UPDATE autor SET nome_autor = ? WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getNome_autor().toUpperCase());
            stmt.setInt(2, autor.getId());
            stmt.executeUpdate();
        }
    }
    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM autor WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}