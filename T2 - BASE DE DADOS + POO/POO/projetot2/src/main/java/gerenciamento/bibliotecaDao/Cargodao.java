package gerenciamento.bibliotecaDao;

import gerenciamento.bibliotecadb.ConexaoBanco;
import gerenciamento.biblioteca.logica.Cargo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cargodao {

    public void salvar(Cargo cargo) throws SQLException {
        String sql = "INSERT INTO cargo (nome_cargo) VALUES (?)";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cargo.getNome_cargo().toUpperCase());
            stmt.executeUpdate();
        }
    }

    public List<Cargo> listarTodos() throws SQLException {
        List<Cargo> lista = new ArrayList<>();
        String sql = "SELECT * FROM cargo ORDER BY nome_cargo";
        try (Connection conn = new ConexaoBanco().getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cargo c = new Cargo(rs.getString("nome_cargo"));
                c.setId(rs.getInt("id"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void atualizar(Cargo cargo) throws SQLException {
        String sql = "UPDATE cargo SET nome_cargo = ? WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cargo.getNome_cargo().toUpperCase());
            stmt.setInt(2, cargo.getId());
            stmt.executeUpdate();
        }
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cargo WHERE id = ?";
        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}