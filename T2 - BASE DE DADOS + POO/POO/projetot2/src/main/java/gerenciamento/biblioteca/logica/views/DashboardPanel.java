package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.DashboardDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class DashboardPanel extends JPanel {
    private JLabel lblDisponiveis, lblLeitores, lblEmprestimos, lblAtrasados;
    private JTable tableAuditoria;
    private DefaultTableModel tableModelAudit;
    private DashboardDAO dao;

    public DashboardPanel() {
        dao = new DashboardDAO();
        setLayout(null);
        setBackground(new Color(245, 247, 250));

        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(30, 20, 200, 30);
        add(lblTitle);

        lblDisponiveis = new JLabel("...");
        lblLeitores = new JLabel("...");
        lblEmprestimos = new JLabel("...");
        lblAtrasados = new JLabel("...");

        add(criarCard("Livros Disponíveis (Soma)", lblDisponiveis, Color.WHITE, 30, 70));
        add(criarCard("Leitores Cadastrados", lblLeitores, Color.WHITE, 260, 70));
        add(criarCard("Empréstimos Ativos", lblEmprestimos, Color.WHITE, 490, 70));
        add(criarCard("Atrasados", lblAtrasados, new Color(255, 220, 220), 720, 70));

        // Tabela de Auditoria
        JLabel lblAudit = new JLabel("Auditoria de Empréstimos");
        lblAudit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblAudit.setBounds(30, 200, 500, 30);
        add(lblAudit);
        
        String[] colunas = {"Data/Hora", "Ação", "Usuário Envolvido", "Detalhes / Responsável"};
        tableModelAudit = new DefaultTableModel(colunas, 0);
        tableAuditoria = new JTable(tableModelAudit);
        JScrollPane scroll = new JScrollPane(tableAuditoria);
        scroll.setBounds(30, 240, 920, 350);
        add(scroll);
        
        atualizarDados();
    }

    public void atualizarDados() {
        lblDisponiveis.setText(String.valueOf(dao.contarLivrosDisponiveis()));
        lblLeitores.setText(String.valueOf(dao.contarUsuarios()));
        lblEmprestimos.setText(String.valueOf(dao.contarEmprestimosAtivos()));
        
        int atrasados = dao.contarAtrasados();
        lblAtrasados.setText(String.valueOf(atrasados));
        lblAtrasados.setForeground(atrasados > 0 ? Color.RED : Color.BLACK);

        // Atualiza Auditoria
        tableModelAudit.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        for (Object[] row : dao.obterAuditoriaRecente()) {
            String data = (row[0] != null) ? sdf.format(row[0]) : "";
            tableModelAudit.addRow(new Object[]{ data, row[1], row[2], row[3] });
        }
    }

    private JPanel criarCard(String titulo, JLabel lblValor, Color corFundo, int x, int y) {
        JPanel card = new JPanel(null);
        card.setBackground(corFundo);
        card.setBounds(x, y, 210, 100);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setBounds(15, 15, 180, 20);
        lblTit.setForeground(Color.GRAY);
        
        lblValor.setBounds(15, 45, 100, 40);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 28));

        card.add(lblTit);
        card.add(lblValor);
        return card;
    }
}