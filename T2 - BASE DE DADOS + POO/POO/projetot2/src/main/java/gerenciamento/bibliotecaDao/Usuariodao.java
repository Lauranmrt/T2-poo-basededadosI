package gerenciamento.bibliotecaDao;


import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Usuario;
import gerenciamento.biblioteca.logica.tratamentodeerros.Validador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuariodao {

    public void salvar(Usuario u) throws Exception {
        String sql = "INSERT INTO usuario (nome_usuario, cpf, email, telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Validador.tratarTexto(u.getNome_usuario()));
            stmt.setString(2, Validador.validarEFormatarCPF(u.getCpf()));
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getTelefone());
            stmt.execute();
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome_usuario(rs.getString("nome_usuario"));
                u.setCpf(rs.getString("cpf"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                lista.add(u);
            }
        }
        return lista;
    }
    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public void atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nome_usuario = ?, email = ?, telefone = ? WHERE id = ?"; 
        
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNome_usuario().toUpperCase());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getTelefone());
            stmt.setInt(4, u.getId());
            stmt.executeUpdate();
        }
    }
}