package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.*;
import gerenciamento.biblioteca.logica.tratamentodeerros.Validador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Obradao {

    // CREATE
    public void salvar(Obra obra, int idAutor, int idEditora, int idGenero) throws Exception {
        String sql = "INSERT INTO obra (titulo, autor_id, editora_id, genero_id, ano_lancamento, isbn) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, Validador.tratarTexto(obra.getTitulo()));
            stmt.setInt(2, idAutor);
            stmt.setInt(3, idEditora);
            stmt.setInt(4, idGenero);
            stmt.setShort(5, obra.getAno_lancamento());
            stmt.setString(6, obra.getIsbn());
            
            stmt.executeUpdate();
        }
    }

    // UPDATE
    public void atualizar(Obra obra, int idAutor, int idEditora, int idGenero) throws Exception {
        String sql = "UPDATE obra SET titulo=?, autor_id=?, editora_id=?, genero_id=?, ano_lancamento=?, isbn=? WHERE id=?";
        
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, Validador.tratarTexto(obra.getTitulo()));
            stmt.setInt(2, idAutor);
            stmt.setInt(3, idEditora);
            stmt.setInt(4, idGenero);
            stmt.setShort(5, obra.getAno_lancamento());
            stmt.setString(6, obra.getIsbn());
            stmt.setInt(7, obra.getId()); // O ID é usado no WHERE
            
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Obra> listarComDetalhes() throws SQLException {
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.*, a.nome_autor, e.nome_editora, g.nome_genero " +
                     "FROM obra o " +
                     "JOIN autor a ON o.autor_id = a.id " +
                     "JOIN editora e ON o.editora_id = e.id " +
                     "JOIN genero g ON o.genero_id = g.id " +
                     "ORDER BY o.titulo";

        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Reconstruindo os objetos para a View poder exibir
                Autor autor = new Autor(rs.getString("nome_autor"));
                autor.setId(rs.getInt("autor_id"));

                Editora editora = new Editora(rs.getString("nome_editora"));
                editora.setId(rs.getInt("editora_id"));

                Genero genero = new Genero(rs.getString("nome_genero"));
                genero.setId(rs.getInt("genero_id"));

                Obra obra = new Obra();
                obra.setId(rs.getInt("id"));
                obra.setTitulo(rs.getString("titulo"));
                obra.setAno_lancamento(rs.getShort("ano_lancamento"));
                obra.setIsbn(rs.getString("isbn"));
                
                // Setando as dependências
                obra.setAutor_id(autor);
                obra.setEditora_id(editora);
                obra.setGenero_id(genero);

                lista.add(obra);
            }
        }
        return lista;
    }

    // DELETE
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM obra WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    // pra carregar ComboBox
    public List<Obra> listarTodas() throws SQLException {
         return listarComDetalhes(); 
    }
}