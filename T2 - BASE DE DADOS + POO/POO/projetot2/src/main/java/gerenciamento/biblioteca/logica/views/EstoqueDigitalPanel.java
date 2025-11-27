package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecadb.ConexaoBanco;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EstoqueDigitalPanel extends JPanel {
    private DefaultTableModel tableModel;

    public EstoqueDigitalPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Estoque Digital Consolidado (Resumo)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID Obra", "Título", "ISBN", "Total Físico", "Disponíveis"};
        tableModel = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        JButton btnAtualizar = new JButton("Atualizar Listagem");
        btnAtualizar.addActionListener(e -> carregarDados());

        add(lblTitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnAtualizar, BorderLayout.SOUTH);

        carregarDados();
    }

    public void carregarDados() {
        tableModel.setRowCount(0);
        // VIEW do sql
        String sql = "SELECT * FROM vw_estoque_consolidado ORDER BY titulo";

        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("obra_id"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getInt("total_fisico"),
                    rs.getInt("quantidade_disponivel")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estoque digital: " + e.getMessage());
        }
    }
}
