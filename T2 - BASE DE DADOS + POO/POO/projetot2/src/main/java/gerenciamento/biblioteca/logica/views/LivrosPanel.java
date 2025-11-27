package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.Autordao;
import gerenciamento.bibliotecaDao.Editoradao;
import gerenciamento.bibliotecaDao.Generodao;
import gerenciamento.bibliotecaDao.Obradao;
import gerenciamento.biblioteca.logica.Autor;
import gerenciamento.biblioteca.logica.Editora;
import gerenciamento.biblioteca.logica.Genero;
import gerenciamento.biblioteca.logica.Obra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LivrosPanel extends JPanel {
    private JTextField txtId, txtTitulo, txtIsbn, txtAno;
    private JComboBox<ComboItem> cbAutor, cbEditora, cbGenero;
    private JTable table;
    private DefaultTableModel tableModel;
    
    // DAOs
    private Obradao obraDAO;
    private Autordao autorDAO;
    private Editoradao editoraDAO;
    private Generodao generoDAO;

    public LivrosPanel() {
        obraDAO = new Obradao();
        autorDAO = new Autordao();
        editoraDAO = new Editoradao();
        generoDAO = new Generodao();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel pnlForm = new JPanel(new GridLayout(7, 2, 5, 5));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Cadastro / Edição de Obras"));

        txtId = new JTextField();
        txtId.setEditable(false); // ID não se mexe manualmente
        txtId.setBackground(new Color(230, 230, 230));
        
        txtTitulo = new JTextField();
        txtIsbn = new JTextField();
        txtAno = new JTextField();
        cbAutor = new JComboBox<>();
        cbEditora = new JComboBox<>();
        cbGenero = new JComboBox<>();

        pnlForm.add(new JLabel("ID (Automático):")); pnlForm.add(txtId);
        pnlForm.add(new JLabel("Título:")); pnlForm.add(txtTitulo);
        pnlForm.add(new JLabel("ISBN:")); pnlForm.add(txtIsbn);
        pnlForm.add(new JLabel("Ano:")); pnlForm.add(txtAno);
        pnlForm.add(new JLabel("Autor:")); pnlForm.add(cbAutor);
        pnlForm.add(new JLabel("Editora:")); pnlForm.add(cbEditora);
        pnlForm.add(new JLabel("Gênero:")); pnlForm.add(cbGenero);

        // BOTÕES
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotoes.setBackground(Color.WHITE);
        
        JButton btnSalvarNovo = criarBotao("Salvar Novo", Estilo.PRINCIPAL);
        JButton btnCarregar = criarBotao("Editar Selecionado (Carregar)", Estilo.AMARELO);
        JButton btnConfirmarEdit = criarBotao("Salvar Alterações", Estilo.VERDE);
        JButton btnExcluir = criarBotao("Excluir", Estilo.VERMELHO);
        
        pnlBotoes.add(btnSalvarNovo);
        pnlBotoes.add(btnCarregar);
        pnlBotoes.add(btnConfirmarEdit);
        pnlBotoes.add(btnExcluir);

        JPanel top = new JPanel(new BorderLayout());
        top.add(pnlForm, BorderLayout.CENTER);
        top.add(pnlBotoes, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        // TABELA
        String[] colunas = {"ID", "Título", "Autor", "Editora", "Gênero", "Ano", "ISBN"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // AÇÕES
        btnSalvarNovo.addActionListener(e -> salvarNovo());
        btnCarregar.addActionListener(e -> carregarParaEdicao());
        btnConfirmarEdit.addActionListener(e -> confirmarEdicao());
        btnExcluir.addActionListener(e -> excluir());

        atualizarTela();
    }
    
    // estilo
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        try {
            btn.setBackground(cor);
            btn.setForeground(Estilo.TEXTO_BOTAO); // Preto
        } catch (NoClassDefFoundError e) {
            btn.setBackground(Color.LIGHT_GRAY);
        }
        return btn;
    }

    public void atualizarTela() {
        carregarCombos();
        atualizarTabela();
        limparCampos();
    }

    private void carregarCombos() {
        try {
            cbAutor.removeAllItems();
            for (Autor a : autorDAO.listarTodos()) cbAutor.addItem(new ComboItem(a.getId(), a.getNome_autor()));

            cbEditora.removeAllItems();
            for (Editora e : editoraDAO.listarTodas()) cbEditora.addItem(new ComboItem(e.getId(), e.getNome_editora()));

            cbGenero.removeAllItems();
            for (Genero g : generoDAO.listarTodos()) cbGenero.addItem(new ComboItem(g.getId(), g.getNome_genero()));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        try {
            for (Obra o : obraDAO.listarComDetalhes()) {
                tableModel.addRow(new Object[]{
                    o.getId(), o.getTitulo(), 
                    o.getAutor_id().getNome_autor(),
                    o.getEditora_id().getNome_editora(), 
                    o.getGenero_id().getNome_genero(),
                    o.getAno_lancamento(), o.getIsbn()
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // CRUD

    private void salvarNovo() {
        try {
            Obra o = montarObraDosCampos();
            obraDAO.salvar(o, getIdCombo(cbAutor), getIdCombo(cbEditora), getIdCombo(cbGenero));
            
            JOptionPane.showMessageDialog(this, "Livro cadastrado!");
            atualizarTela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void carregarParaEdicao() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela primeiro.");
            return;
        }

        // preenche os campos de texto com dados da tabela
        txtId.setText(table.getValueAt(row, 0).toString());
        txtTitulo.setText(table.getValueAt(row, 1).toString());
        txtAno.setText(table.getValueAt(row, 5).toString());
        txtIsbn.setText(table.getValueAt(row, 6).toString());

        // seleciona os itens nos ComboBoxes
        String nomeAutor = table.getValueAt(row, 2).toString();
        String nomeEditora = table.getValueAt(row, 3).toString();
        String nomeGenero = table.getValueAt(row, 4).toString();

        selecionarItemCombo(cbAutor, nomeAutor);
        selecionarItemCombo(cbEditora, nomeEditora);
        selecionarItemCombo(cbGenero, nomeGenero);
        
        JOptionPane.showMessageDialog(this, "Dados carregados no formulário acima. Faça as alterações e clique em 'Salvar Alterações'.");
    }

    private void confirmarEdicao() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carregue um livro para edição primeiro.");
            return;
        }

        try {
            Obra o = montarObraDosCampos();
            o.setId(Integer.parseInt(txtId.getText())); // seta o ID para o UPDATE funcionar

            obraDAO.atualizar(o, getIdCombo(cbAutor), getIdCombo(cbEditora), getIdCombo(cbGenero));
            
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            atualizarTela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }

    private void excluir() {
        int row = table.getSelectedRow();
        if (row != -1) {
             int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir este livro?", "Confirmação", JOptionPane.YES_NO_OPTION);
             if (confirm == JOptionPane.YES_OPTION) {
                 try {
                     obraDAO.deletar((int)table.getValueAt(row, 0));
                     atualizarTela();
                 } catch(Exception e) { JOptionPane.showMessageDialog(this, "Erro (Verifique se há exemplares): " + e.getMessage()); }
             }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione para excluir.");
        }
    }
    
    // AUXILIARES

    private Obra montarObraDosCampos() throws Exception {
        if (txtTitulo.getText().isEmpty() || txtAno.getText().isEmpty()) throw new Exception("Preencha Título e Ano.");
        
        Obra o = new Obra();
        o.setTitulo(txtTitulo.getText());
        o.setIsbn(txtIsbn.getText());
        o.setAno_lancamento(Short.parseShort(txtAno.getText()));
        return o;
    }

    private void limparCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtIsbn.setText("");
        txtAno.setText("");
        if(cbAutor.getItemCount() > 0) cbAutor.setSelectedIndex(0);
    }

    private int getIdCombo(JComboBox<ComboItem> cb) {
        return ((ComboItem) cb.getSelectedItem()).getId();
    }

    // limpa o ComboBox para achar o item com o texto igual ao da tabela e seleciona ele
    private void selecionarItemCombo(JComboBox<ComboItem> cb, String textoProcurado) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAt(i).toString().equals(textoProcurado)) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }
    
    // subclasse
    class ComboItem {
        int id; String text;
        ComboItem(int id, String t) { this.id = id; this.text = t; }
        public int getId() { return id; }
        public String toString() { return text; }
    }
}