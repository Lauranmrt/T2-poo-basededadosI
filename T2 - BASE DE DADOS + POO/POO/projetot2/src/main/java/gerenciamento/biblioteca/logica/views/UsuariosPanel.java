package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.Usuariodao;
import gerenciamento.biblioteca.logica.Usuario;
import gerenciamento.biblioteca.logica.tratamentodeerros.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuariosPanel extends JPanel {
    private JTextField txtNome, txtCpf, txtEmail, txtTelefone;
    private JTable table;
    private DefaultTableModel tableModel;
    private Usuariodao dao;

    public UsuariosPanel() {
        dao = new Usuariodao();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Usuários"));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();

        pnlForm.add(new JLabel("Nome:")); pnlForm.add(txtNome);
        pnlForm.add(new JLabel("CPF:")); pnlForm.add(txtCpf);
        pnlForm.add(new JLabel("Email:")); pnlForm.add(txtEmail);
        pnlForm.add(new JLabel("Telefone:")); pnlForm.add(txtTelefone);

        // Botões
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotoes.setBackground(Color.WHITE);
        
        JButton btnSalvar = criarBotao("Salvar", Estilo.PRINCIPAL);
        JButton btnEditar = criarBotao("Editar Selecionado", Estilo.AMARELO);
        JButton btnExcluir = criarBotao("Excluir", Estilo.VERMELHO);
        
        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnEditar);
        pnlBotoes.add(btnExcluir);

        JPanel top = new JPanel(new BorderLayout());
        top.add(pnlForm, BorderLayout.CENTER);
        top.add(pnlBotoes, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "CPF", "Email", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Ações
        btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        
        atualizarTabela();
    }
    
    // Botão
    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setBackground(corFundo);
        btn.setForeground(Estilo.TEXTO_BOTAO); // Preto
        return btn;
    }

    private void salvar() {
        try {
            Usuario u = new Usuario();
            u.setNome_usuario(txtNome.getText());
            u.setCpf(txtCpf.getText());
            Validador.validarEFormatarCPF(txtCpf.getText()); // teste
            u.setEmail(txtEmail.getText());
            u.setTelefone(txtTelefone.getText());
            dao.salvar(u);
            JOptionPane.showMessageDialog(this, "Salvo!");
            limpar(); atualizarTabela();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void editar() {
        int row = table.getSelectedRow();
        if(row == -1) return;
        
        try {
            int id = (int) table.getValueAt(row, 0);
            String novoNome = JOptionPane.showInputDialog(this, "Editar Nome:", table.getValueAt(row, 1));
            String novoEmail = JOptionPane.showInputDialog(this, "Editar Email:", table.getValueAt(row, 3));
            String novoTel = JOptionPane.showInputDialog(this, "Editar Telefone:", table.getValueAt(row, 4));
            
            if(novoNome != null) {
                Usuario u = new Usuario();
                u.setId(id);
                u.setNome_usuario(novoNome);
                u.setEmail(novoEmail);
                u.setTelefone(novoTel);
                dao.atualizar(u);
                JOptionPane.showMessageDialog(this, "Atualizado!");
                atualizarTabela();
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void excluir() {
        int row = table.getSelectedRow();
        if(row != -1) {
            try { dao.deletar((int) table.getValueAt(row, 0)); atualizarTabela(); }
            catch(Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        try {
            for(Usuario u : dao.listarTodos()) {
                tableModel.addRow(new Object[]{u.getId(), u.getNome_usuario(), u.getCpf(), u.getEmail(), u.getTelefone()});
            }
        } catch(Exception e){}
    }
    
    private void limpar() { txtNome.setText(""); txtCpf.setText(""); txtEmail.setText(""); txtTelefone.setText(""); }
}