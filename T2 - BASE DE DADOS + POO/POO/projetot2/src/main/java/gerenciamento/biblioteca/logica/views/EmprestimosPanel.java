package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.*;
import gerenciamento.biblioteca.logica.Obra;
import gerenciamento.biblioteca.logica.Usuario;
import gerenciamento.bibliotecadb.ConexaoBanco;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmprestimosPanel extends JPanel {
    private JComboBox<ComboItem> cbLivros;
    private JComboBox<ComboItem> cbUsuarios;
    private JTable table;
    private DefaultTableModel tableModel;
    private Emprestimodao emprestimoDAO;

    public EmprestimosPanel() {
        emprestimoDAO = new Emprestimodao();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Cadastro
        JPanel pnlControle = new JPanel(new GridLayout(1, 3, 10, 10));
        pnlControle.setBackground(Color.WHITE);
        pnlControle.setBorder(BorderFactory.createTitledBorder("Novo Empréstimo"));

        cbLivros = new JComboBox<>();
        cbUsuarios = new JComboBox<>();
        JButton btnRegistrar = criarBotao("Registrar Saída", Estilo.PRINCIPAL);

        pnlControle.add(montarComboPanel("Obra:", cbLivros));
        pnlControle.add(montarComboPanel("Leitor:", cbUsuarios));
        pnlControle.add(btnRegistrar);

        // Tabela
        String[] colunas = {"ID", "Obra", "Usuário", "Data Saída", "Prev. Devolução", "Devolução Real", "Status"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isSel, boolean hasFoc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, isSel, hasFoc, row, col);
                String status = (String) t.getValueAt(row, 6);
                if ("PENDENTE".equals(status)) {
                    c.setForeground(Color.RED);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(new Color(0, 100, 0));
                }
                c.setBackground(isSel ? new Color(220, 230, 255) : Color.WHITE);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Histórico e Gerenciamento"));

        // Ações
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlActions.setBackground(Color.WHITE);

        JButton btnAtualizar = criarBotao("Atualizar Tabela", Estilo.PRINCIPAL);
        JButton btnEditar = criarBotao("Editar Data", Estilo.AMARELO);
        JButton btnDevolver = criarBotao("Devolver", Estilo.VERDE);
        JButton btnExcluir = criarBotao("Excluir", Estilo.VERMELHO);
        
        pnlActions.add(btnAtualizar);
        pnlActions.add(btnEditar);
        pnlActions.add(btnDevolver);
        pnlActions.add(btnExcluir);

        add(pnlControle, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(pnlActions, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(e -> registrarEmprestimo());
        btnDevolver.addActionListener(e -> realizarDevolucao());
        btnEditar.addActionListener(e -> editarData());
        btnExcluir.addActionListener(e -> excluirEmprestimo());
        btnAtualizar.addActionListener(e -> atualizarTela());

        atualizarTela();
    }
    
    // Botão
    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        try {
            btn.setBackground(corFundo);
            btn.setForeground(Color.BLACK); // Texto
        } catch (Exception e) {
            btn.setBackground(Color.LIGHT_GRAY);
        }
        return btn;
    }

    private void registrarEmprestimo() {
        ComboItem livro = (ComboItem) cbLivros.getSelectedItem();
        ComboItem user = (ComboItem) cbUsuarios.getSelectedItem();
        if (livro == null || user == null) return;

        try {
            String msg = emprestimoDAO.registrarEmprestimo(user.getId(), livro.getId());
            JOptionPane.showMessageDialog(this, msg);
            atualizarTabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void realizarDevolucao() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Selecione um item."); return; }
        int id = (int) table.getValueAt(row, 0);
        String status = (String) table.getValueAt(row, 6);

        if ("DEVOLVIDO".equals(status)) { JOptionPane.showMessageDialog(this, "Já devolvido."); return; }

        try {
            String msg = emprestimoDAO.registrarDevolucao(id);
            JOptionPane.showMessageDialog(this, msg);
            atualizarTabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void editarData() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Selecione um item."); return; }
        int id = (int) table.getValueAt(row, 0);
        String atual = (String) table.getValueAt(row, 4);
        
        String nova = JOptionPane.showInputDialog(this, "Nova Data (YYYY-MM-DD):", atual);
        if (nova != null && !nova.isEmpty()) {
            try {
                emprestimoDAO.atualizarDatas(id, nova);
                JOptionPane.showMessageDialog(this, "Data atualizada!");
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }
    
    private void excluirEmprestimo() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Selecione um item."); return; }
        int id = (int) table.getValueAt(row, 0);
        
        if (JOptionPane.showConfirmDialog(this, "Confirmar exclusão? Auditoria será registrada.", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                emprestimoDAO.deletar(id);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Excluído.");
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }

    public void atualizarTela() { carregarCombos(); atualizarTabela(); }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdfHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "SELECT e.id, o.titulo, u.nome_usuario, e.data_emprestimo, e.data_previsao_devolucao, e.data_devolucao_real, e.status_emprestimo FROM emprestimo e JOIN usuario u ON e.usuario_id = u.id JOIN exemplar ex ON e.exemplar_id = ex.id JOIN obra o ON ex.obra_id = o.id ORDER BY e.data_emprestimo DESC";

        try (Connection conn = new ConexaoBanco().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                Timestamp tsSaida = rs.getTimestamp("data_emprestimo");
                Date dtPrev = rs.getDate("data_previsao_devolucao");
                Timestamp tsReal = rs.getTimestamp("data_devolucao_real");

                tableModel.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("titulo"), rs.getString("nome_usuario"),
                    (tsSaida!=null)?sdfHora.format(tsSaida):"",
                    (dtPrev!=null)?sdfData.format(dtPrev):"",
                    (tsReal!=null)?sdfHora.format(tsReal):"-",
                    rs.getString("status_emprestimo")
                });
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void carregarCombos() {
        cbUsuarios.removeAllItems(); cbLivros.removeAllItems();
        try {
            Usuariodao uDao = new Usuariodao();
            for (Usuario u : uDao.listarTodos()) cbUsuarios.addItem(new ComboItem(u.getId(), u.getNome_usuario()));
            Obradao oDao = new Obradao();
            for (Obra o : oDao.listarTodas()) cbLivros.addItem(new ComboItem(o.getId(), o.getTitulo()));
        } catch (Exception e) {}
    }

    private JPanel montarComboPanel(String l, JComboBox b) {
        JPanel p = new JPanel(new BorderLayout()); p.setBackground(Color.WHITE);
        p.add(new JLabel(l), BorderLayout.NORTH); p.add(b, BorderLayout.CENTER); return p;
    }

    class ComboItem {
        int id; String l;
        ComboItem(int id, String l) { this.id = id; this.l = l; }
        public int getId() { return id; }
        public String toString() { return l; }
    }
}