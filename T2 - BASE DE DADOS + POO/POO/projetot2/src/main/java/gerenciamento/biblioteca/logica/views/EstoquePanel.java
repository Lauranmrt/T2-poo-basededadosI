package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.*;
import gerenciamento.biblioteca.logica.Obra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstoquePanel extends JPanel {
    private JComboBox<ComboItem> cbObras;
    private DefaultTableModel tableModel;
    private Exemplardao exemplarDAO;
    private Obradao obraDAO;

    public EstoquePanel() {
        exemplarDAO = new Exemplardao();
        obraDAO = new Obradao();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // add Exemplar
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Estoque Físico"));

        cbObras = new JComboBox<>();
        cbObras.setPreferredSize(new Dimension(300, 30));
        
        JButton btnAdd = new JButton("Adicionar Unidade ao Acervo");
        btnAdd.setBackground(new Color(30, 60, 255));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlTop.add(new JLabel("Selecione a Obra para adicionar unidade:"));
        pnlTop.add(cbObras);
        pnlTop.add(btnAdd);

        // Tabela de Exemplares
        String[] cols = {"ID Exemplar", "Título da Obra", "Status Atual"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Inventário Completo"));

        add(pnlTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> adicionarExemplar());
        
        atualizarTela();
    }

    // MainFrame usa
    public void atualizarTela() {
        carregarComboObras();
        carregarTabelaEstoque();
    }

    private void carregarComboObras() {
        cbObras.removeAllItems();
        try {
            // listarTodas do dao para preencher o combo
            List<Obra> obras = obraDAO.listarTodas();
            for (Obra o : obras) {
                cbObras.addItem(new ComboItem(o.getId(), o.getTitulo()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarTabelaEstoque() {
        tableModel.setRowCount(0);
        try {
            // dao retorna obj com {id, titulo, status}
            List<Object[]> estoque = exemplarDAO.listarEstoque();
            for (Object[] row : estoque) {
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adicionarExemplar() {
        ComboItem itemSelecionado = (ComboItem) cbObras.getSelectedItem();
        if (itemSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma obra primeiro.");
            return;
        }
        
        try {
            exemplarDAO.adicionarExemplar(itemSelecionado.getId());
            JOptionPane.showMessageDialog(this, "Novo exemplar registrado com sucesso!");
            atualizarTela(); // att a tabela para mostrar o livro
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + e.getMessage());
        }
    }

    // para guardar ID e Nome
    class ComboItem {
        private int id;
        private String text;

        public ComboItem(int id, String text) {
            this.id = id;
            this.text = text;
        }

        public int getId() { return id; }
        
        @Override
        public String toString() { return text; }
    }
}
