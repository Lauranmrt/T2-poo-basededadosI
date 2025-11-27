package gerenciamento.biblioteca.logica.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainContent;
    private CardLayout cardLayout;

    private DashboardPanel dashPanel;
    private EmprestimosPanel empPanel;
    private UsuariosPanel userPanel;
    private LivrosPanel bookPanel;
    private EstoquePanel estoquePanel;
    private EstoqueDigitalPanel estoqueDigPanel;
    private AuxiliaresPanel auxPanel;
    private FuncionariosPanel funcPanel;

    public MainFrame() {
        // Janela
        setTitle("Sistema de Gerenciamento Bibliotecário");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        dashPanel = new DashboardPanel();
        empPanel = new EmprestimosPanel();
        userPanel = new UsuariosPanel();
        bookPanel = new LivrosPanel();
        estoquePanel = new EstoquePanel();
        estoqueDigPanel = new EstoqueDigitalPanel();
        auxPanel = new AuxiliaresPanel();
        funcPanel = new FuncionariosPanel();

        // Menu Lateral
        JPanel sidebar = new JPanel();
        try {
            sidebar.setBackground(Estilo.FUNDO_MENU); 
        } catch (NoClassDefFoundError e) {
            sidebar.setBackground(new Color(30, 60, 255));
        }
        
        sidebar.setPreferredSize(new Dimension(270, 768));
        sidebar.setLayout(new GridLayout(13, 1, 5, 5));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel lblTitle = new JLabel("Biblioteca", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        sidebar.add(lblTitle);
        sidebar.add(new JSeparator());

        // Área
        mainContent = new JPanel();
        cardLayout = new CardLayout();
        mainContent.setLayout(cardLayout);
        mainContent.setBackground(Color.WHITE);

        // add as telas
        mainContent.add(dashPanel, "DASH");
        mainContent.add(empPanel, "EMPRESTIMOS");
        mainContent.add(bookPanel, "LIVROS");
        mainContent.add(estoquePanel, "ESTOQUE_FISICO");
        mainContent.add(estoqueDigPanel, "ESTOQUE_DIGITAL");
        mainContent.add(userPanel, "USUARIOS");
        mainContent.add(funcPanel, "FUNCIONARIOS");
        mainContent.add(auxPanel, "AUXILIARES");

        // Botões do Menu
        
        adicionarBotao(sidebar, "Dashboard", e -> {
            dashPanel.atualizarDados();
            cardLayout.show(mainContent, "DASH");
        });

        adicionarBotao(sidebar, "Empréstimos", e -> {
            empPanel.atualizarTela();
            cardLayout.show(mainContent, "EMPRESTIMOS");
        });

        adicionarBotao(sidebar, "Livros (Obras)", e -> {
            bookPanel.atualizarTela();
            cardLayout.show(mainContent, "LIVROS");
        });

        adicionarBotao(sidebar, "Estoque Físico (Add)", e -> {
            estoquePanel.atualizarTela();
            cardLayout.show(mainContent, "ESTOQUE_FISICO");
        });

        adicionarBotao(sidebar, "Estoque Digital (Resumo)", e -> {
            estoqueDigPanel.carregarDados();
            cardLayout.show(mainContent, "ESTOQUE_DIGITAL");
        });

        adicionarBotao(sidebar, "Leitores / Usuários", e -> {
            cardLayout.show(mainContent, "USUARIOS");
        });

        adicionarBotao(sidebar, "Gerir Funcionários", e -> {
            funcPanel.atualizarTela();
            cardLayout.show(mainContent, "FUNCIONARIOS");
        });
        
        adicionarBotao(sidebar, "Cadastros Gerais", e -> {
            cardLayout.show(mainContent, "AUXILIARES");
        });

        // Botão de Sair
        JButton btnSair = new JButton("Sair / Logout");
        estilizarBotao(btnSair);
        try {
            btnSair.setBackground(Estilo.VERMELHO);
        } catch (NoClassDefFoundError e) {
            btnSair.setBackground(new Color(220, 53, 69));
        }
        
        btnSair.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
        

        sidebar.add(btnSair);

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    private void adicionarBotao(JPanel painel, String texto, ActionListener acao) {
        JButton btn = new JButton(texto);
        estilizarBotao(btn);
        btn.addActionListener(acao);
        painel.add(btn);
    }

    private void estilizarBotao(JButton btn) {
        btn.setForeground(Color.WHITE);
        try {
            btn.setBackground(Estilo.PRINCIPAL);
        } catch (NoClassDefFoundError e) {
            btn.setBackground(new Color(60, 90, 255));
        }
        
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}