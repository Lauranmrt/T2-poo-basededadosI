package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.Funcionariodao;
import gerenciamento.biblioteca.logica.Funcionario;
import gerenciamento.biblioteca.logica.tratamentodeerros.Sessao;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    
    public LoginFrame() {
        setTitle("Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        try {
            lblTitulo.setFont(Estilo.FONT_TITULO);
            lblTitulo.setForeground(Estilo.PRINCIPAL);
        } catch (NoClassDefFoundError e) {
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
            lblTitulo.setForeground(Color.BLUE);
        }

        // Painel Usuário
        JPanel pnlUser = new JPanel();
        JTextField txtUser = new JTextField(20);
        pnlUser.add(new JLabel("Login:"));
        pnlUser.add(txtUser);

        // Painel Senha
        JPanel pnlPass = new JPanel();
        JPasswordField txtPass = new JPasswordField(20);
        pnlPass.add(new JLabel("Senha:"));
        pnlPass.add(txtPass);

        // Botão Entrar
        JPanel pnlBtn = new JPanel();
        JButton btnLogin = new JButton("Entrar");
        
        // estilo do Botão
        try {
            btnLogin.setBackground(Estilo.PRINCIPAL);
            btnLogin.setForeground(Color.BLACK);
            btnLogin.setFont(Estilo.FONT_TEXTO);
        } catch (NoClassDefFoundError e) {
            btnLogin.setBackground(Color.BLUE);
            btnLogin.setForeground(Color.BLACK);
        }
        
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new Dimension(150, 40));
        pnlBtn.add(btnLogin);

        // add componentes na tela
        add(lblTitulo);
        add(pnlUser);
        add(pnlPass);
        add(pnlBtn);
        
        JLabel lblFooter = new JLabel("v2.0 - Sistema Bibliotecário", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFooter.setForeground(Color.GRAY);
        add(lblFooter);

        // AÇÃO DO BOTÃO
        btnLogin.addActionListener(e -> {
            try {
                Funcionariodao dao = new Funcionariodao();
                
                // Tenta autenticar
                Funcionario f = dao.autenticar(txtUser.getText(), new String(txtPass.getPassword()));
                
                if (f != null) {
                    // salva na Sessão para a auditoria funcionar
                    Sessao.setFuncionarioLogado(f);

                    JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + f.getLogin() + "!");
                    
                    // abre a tela principal
                    new MainFrame().setVisible(true);
                    
                    // fecha a tela de login
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Login ou senha incorretos.", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro de conexão ou validação: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        // deixa apertar ENTER na caixa de senha para logar
        getRootPane().setDefaultButton(btnLogin);
    }
}