package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.*;
import gerenciamento.biblioteca.logica.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FuncionariosPanel extends JPanel {
    private JComboBox<ComboItem> cbUsuarios;
    private JComboBox<ComboItem> cbCargos;
    private JTextField txtSalario, txtLogin;
    private JPasswordField txtSenha;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private Usuariodao usuarioDAO;
    private Funcionariodao funcionarioDAO;
    private Cargodao cargoDAO;

    public FuncionariosPanel() {
        usuarioDAO = new Usuariodao();
        funcionarioDAO = new Funcionariodao();
        cargoDAO = new Cargodao();

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Gestão de Funcionários"));
        pnlForm.setBackground(Color.WHITE);

        cbUsuarios = new JComboBox<>();
        cbCargos = new JComboBox<>();
        txtSalario = new JTextField();
        txtLogin = new JTextField();
        txtSenha = new JPasswordField();
        
        JButton btnSalvar = criarBotao("Contratar", Estilo.PRINCIPAL);

        pnlForm.add(new JLabel("Usuário:")); pnlForm.add(cbUsuarios);
        pnlForm.add(new JLabel("Cargo:")); pnlForm.add(cbCargos);
        pnlForm.add(new JLabel("Salário:")); pnlForm.add(txtSalario);
        pnlForm.add(new JLabel("Login:")); pnlForm.add(txtLogin);
        pnlForm.add(new JLabel("Senha:")); pnlForm.add(txtSenha);
        pnlForm.add(new JLabel("")); pnlForm.add(btnSalvar);

        // Grid
        String[] colunas = {"ID", "Nome", "Cargo", "Salário", "Login"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);
        
        // Botões de Ação
        JPanel pnlSul = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSul.setBackground(Color.WHITE);
        JButton btnEditar = criarBotao("Editar Dados", Estilo.AMARELO);
        JButton btnExcluir = criarBotao("Demitir", Estilo.VERMELHO);
        pnlSul.add(btnEditar);
        pnlSul.add(btnExcluir);

        add(pnlForm, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlSul, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        
        atualizarTela();
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Estilo.TEXTO_BOTAO); // Preto
        return btn;
    }

    public void atualizarTela() {
        carregarCombos();
        carregarGrid();
    }

    private void carregarCombos() {
        try {
            cbUsuarios.removeAllItems();
            for(Usuario u : usuarioDAO.listarTodos()) cbUsuarios.addItem(new ComboItem(u.getId(), u.getNome_usuario()));
            cbCargos.removeAllItems();
            for(Cargo c : cargoDAO.listarTodos()) cbCargos.addItem(new ComboItem(c.getId(), c.getNome_cargo()));
        } catch(Exception e){}
    }

    private void carregarGrid() {
        tableModel.setRowCount(0);
        try {
            for(Object[] row : funcionarioDAO.listarTodosComDetalhes()) tableModel.addRow(row);
        } catch(Exception e){}
    }

    private void salvar() {
        try {
            ComboItem user = (ComboItem)cbUsuarios.getSelectedItem();
            ComboItem cargo = (ComboItem)cbCargos.getSelectedItem();
            funcionarioDAO.cadastrar(user.getId(), cargo.getId(), Double.parseDouble(txtSalario.getText()), txtLogin.getText(), new String(txtSenha.getPassword()));
            JOptionPane.showMessageDialog(this, "Sucesso!");
            atualizarTela();
        } catch(Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void editar() {
        int row = table.getSelectedRow();
        if(row == -1) return;
        try {
            int idFunc = (int)table.getValueAt(row, 0);
            String novoSalario = JOptionPane.showInputDialog("Novo Salário:", table.getValueAt(row, 3));
            String novoLogin = JOptionPane.showInputDialog("Novo Login:", table.getValueAt(row, 4));
            
            // pega o cargo atual do combo
            ComboItem cargoAtual = (ComboItem)cbCargos.getSelectedItem(); 
            
            if(novoSalario != null && cargoAtual != null) {
                funcionarioDAO.atualizar(idFunc, cargoAtual.getId(), Double.parseDouble(novoSalario), novoLogin);
                JOptionPane.showMessageDialog(this, "Atualizado!");
                atualizarTela();
            }
        } catch(Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void excluir() {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para excluir.");
                return;
            }

            // pega dds da linha selecionada na tabela
            int idParaExcluir = (int) table.getValueAt(row, 0); 
            String loginParaExcluir = (String) table.getValueAt(row, 4); // 4 é o Login

            // quem está logado no sistema agora
            gerenciamento.biblioteca.logica.Funcionario logado = gerenciamento.biblioteca.logica.tratamentodeerros.Sessao.getFuncionarioLogado();
            
            // BLOQUEIO DE SEGURANÇA
            if (logado != null) {
                boolean mesmoId = (idParaExcluir == logado.getId());
                boolean mesmoLogin = loginParaExcluir.equals(logado.getLogin());

                // Se o ID bater OU o Login bater, bloqueia
                if (mesmoId || mesmoLogin) {
                    JOptionPane.showMessageDialog(this, 
                        "BLOQUEIO DE SEGURANÇA:\n\n" +
                        "Você está logado como '" + logado.getLogin() + "'.\n" +
                        "O sistema não permite que você exclua seu próprio usuário.\n" +
                        "Peça para outro administrador realizar esta ação.",
                        "Ação Negada", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            // ----------------------------------------

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja demitir o funcionário '" + loginParaExcluir + "'?", 
                "Confirmar Demissão", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    funcionarioDAO.deletar(idParaExcluir);
                    atualizarTela();
                    JOptionPane.showMessageDialog(this, "Funcionário removido com sucesso.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
                }
            }
        }

    class ComboItem {
        int id; String text;
        ComboItem(int id, String t){this.id=id;this.text=t;}
        public int getId(){return id;}
        public String toString(){return text;}
    }
}