package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Funcionario;
import gerenciamento.biblioteca.logica.tratamentodeerros.Validador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Funcionariodao {

    public Funcionario autenticar(String login, String senha) throws Exception {
            // validação
            if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
                return null;
            }
            
            // pra garantir que o ID vem
            String sql = "SELECT * FROM funcionario WHERE login = ? AND senha = ?";
            
            try (Connection conn = new ConexaoBanco().getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, login);
                stmt.setString(2, senha);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Funcionario f = new Funcionario();
                        // pega o ID aqui
                        f.setId(rs.getInt("id")); 
                        f.setLogin(rs.getString("login"));
                        f.setSalario(rs.getDouble("salario"));
                        return f;
                    }
                }
            }
            return null;
        }

    public void cadastrar(int usuarioId, int cargoId, double salario, String login, String senha) throws Exception {
        // Validações
        Validador.validarSenha(senha);
        if (login.contains(" ")) throw new Exception("Login não pode ter espaços.");

        String sql = "INSERT INTO funcionario (usuario_id, cargo_id, salario, login, senha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, cargoId);
            stmt.setDouble(3, salario);
            stmt.setString(4, login);
            stmt.setString(5, senha);
            stmt.executeUpdate();
        }
    }

    public List<Object[]> listarTodosComDetalhes() throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT f.id, u.nome_usuario, c.nome_cargo, f.salario, f.login " +
                     "FROM funcionario f " +
                     "JOIN usuario u ON f.usuario_id = u.id " +
                     "JOIN cargo c ON f.cargo_id = c.id " +
                     "ORDER BY u.nome_usuario";
                     
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome_usuario"),
                    rs.getString("nome_cargo"),
                    rs.getDouble("salario"),
                    rs.getString("login")
                });
            }
        }
        return lista;
    }
    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizar(int idFuncionario, int cargoId, double salario, String login) throws SQLException {
        String sql = "UPDATE funcionario SET cargo_id = ?, salario = ?, login = ? WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cargoId);
            stmt.setDouble(2, salario);
            stmt.setString(3, login);
            stmt.setInt(4, idFuncionario);
            stmt.executeUpdate();
        }
    }
}