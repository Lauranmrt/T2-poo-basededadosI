package gerenciamento.biblioteca.logica.views;

import gerenciamento.bibliotecaDao.*;
import gerenciamento.biblioteca.logica.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AuxiliaresPanel extends JPanel {
    
    public AuxiliaresPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JTabbedPane tabs = new JTabbedPane();
        
        // Painel de CRUD
        tabs.addTab("Autores", new CrudAutorPanel());
        tabs.addTab("Editoras", new CrudEditoraPanel());
        tabs.addTab("Gêneros", new CrudGeneroPanel());
        tabs.addTab("Cargos", new CrudCargoPanel());
        
        add(tabs, BorderLayout.CENTER);
    }

    // Painel de AUTOR
    class CrudAutorPanel extends JPanel {
        private JTextField txtNome;
        private JTable table;
        private DefaultTableModel model;
        private Autordao dao = new Autordao();

        public CrudAutorPanel() {
            initUI("Nome do Autor:");
            atualizarTabela();
        }

        private void initUI(String label) {
            setLayout(new BorderLayout(5, 5));
            JPanel pnlForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
            txtNome = new JTextField(20);
            JButton btnSalvar = new JButton("Salvar");
            JButton btnEditar = new JButton("Editar Selecionado");
            JButton btnExcluir = new JButton("Excluir Selecionado");

            pnlForm.add(new JLabel(label)); pnlForm.add(txtNome);
            pnlForm.add(btnSalvar); pnlForm.add(btnEditar); pnlForm.add(btnExcluir);

            model = new DefaultTableModel(new String[]{"ID", "Nome"}, 0);
            table = new JTable(model);

            add(pnlForm, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnSalvar.addActionListener(e -> salvar());
            btnEditar.addActionListener(e -> editar());
            btnExcluir.addActionListener(e -> excluir());
        }

        private void atualizarTabela() {
            model.setRowCount(0);
            try { for (Autor a : dao.listarTodos()) model.addRow(new Object[]{a.getId(), a.getNome_autor()}); } catch (Exception e) {}
        }

        private void salvar() {
            try { dao.salvar(new Autor(txtNome.getText())); atualizarTabela(); txtNome.setText(""); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
        }

        private void editar() {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) table.getValueAt(row, 0);
            String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", table.getValueAt(row, 1));
            if (novoNome != null) {
                try { 
                    Autor a = new Autor(novoNome); a.setId(id); dao.atualizar(a); atualizarTabela(); 
                } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
            }
        }

        private void excluir() {
            int row = table.getSelectedRow();
            if (row != -1) {
                try { dao.deletar((int) table.getValueAt(row, 0)); atualizarTabela(); } 
                catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro (pode estar em uso): " + e.getMessage()); }
            }
        }
    }

    // Painel de EDITORA
    class CrudEditoraPanel extends JPanel {
        private JTextField txtNome;
        private JTable table;
        private DefaultTableModel model;
        private Editoradao dao = new Editoradao();

        public CrudEditoraPanel() {
            setLayout(new BorderLayout(5, 5));
            JPanel pnlForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
            txtNome = new JTextField(20);
            JButton btnSalvar = new JButton("Salvar");
            JButton btnEditar = new JButton("Editar");
            JButton btnExcluir = new JButton("Excluir");

            pnlForm.add(new JLabel("Nome Editora:")); pnlForm.add(txtNome);
            pnlForm.add(btnSalvar); pnlForm.add(btnEditar); pnlForm.add(btnExcluir);

            model = new DefaultTableModel(new String[]{"ID", "Nome"}, 0);
            table = new JTable(model);

            add(pnlForm, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnSalvar.addActionListener(e -> {
                try { dao.salvar(new Editora(txtNome.getText())); atualizarTabela(); txtNome.setText(""); } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            });
            btnEditar.addActionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) {
                    String val = JOptionPane.showInputDialog(this, "Editar:", table.getValueAt(row, 1));
                    if(val!=null) { Editora obj = new Editora(val); obj.setId((int)table.getValueAt(row,0)); try{ dao.atualizar(obj); atualizarTabela();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}}
                }
            });
            btnExcluir.addActionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) try{ dao.deletar((int)table.getValueAt(row,0)); atualizarTabela();}catch(Exception ex){JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());}
            });
            atualizarTabela();
        }
        void atualizarTabela() {
            model.setRowCount(0);
            try { for(Editora e : dao.listarTodas()) model.addRow(new Object[]{e.getId(), e.getNome_editora()}); } catch(Exception ex){}
        }
    }

    // Painel de GENERO
    class CrudGeneroPanel extends JPanel {
        private JTextField txtNome;
        private JTable table;
        private DefaultTableModel model;
        private Generodao dao = new Generodao();

        public CrudGeneroPanel() {
            setLayout(new BorderLayout(5, 5));
            JPanel pnlForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
            txtNome = new JTextField(20);
            JButton btnSalvar = new JButton("Salvar");
            JButton btnEditar = new JButton("Editar");
            JButton btnExcluir = new JButton("Excluir");

            pnlForm.add(new JLabel("Gênero:")); pnlForm.add(txtNome);
            pnlForm.add(btnSalvar); pnlForm.add(btnEditar); pnlForm.add(btnExcluir);

            model = new DefaultTableModel(new String[]{"ID", "Nome"}, 0);
            table = new JTable(model);
            add(pnlForm, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnSalvar.addActionListener(e -> { try { dao.salvar(new Genero(txtNome.getText())); atualizarTabela(); txtNome.setText(""); } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); } });
            btnEditar.addActionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) {
                    String val = JOptionPane.showInputDialog(this, "Editar:", table.getValueAt(row, 1));
                    if(val!=null) { Genero obj = new Genero(val); obj.setId((int)table.getValueAt(row,0)); try{ dao.atualizar(obj); atualizarTabela();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}}
                }
            });
            btnExcluir.addActionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) try{ dao.deletar((int)table.getValueAt(row,0)); atualizarTabela();}catch(Exception ex){JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());}
            });
            atualizarTabela();
        }
        void atualizarTabela() { model.setRowCount(0); try { for(Genero g : dao.listarTodos()) model.addRow(new Object[]{g.getId(), g.getNome_genero()}); } catch(Exception ex){} }
    }

    // Painel de CARGO
    class CrudCargoPanel extends JPanel {
        private JTextField txtNome;
        private JTable table;
        private DefaultTableModel model;
        private Cargodao dao = new Cargodao();

        public CrudCargoPanel() {
            setLayout(new BorderLayout(5, 5));
            JPanel pnlForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
            txtNome = new JTextField(20);
            JButton btnSalvar = new JButton("Salvar");
            JButton btnEditar = new JButton("Editar");
            JButton btnExcluir = new JButton("Excluir");

            pnlForm.add(new JLabel("Cargo:")); pnlForm.add(txtNome);
            pnlForm.add(btnSalvar); pnlForm.add(btnEditar); pnlForm.add(btnExcluir);

            model = new DefaultTableModel(new String[]{"ID", "Nome"}, 0);
            table = new JTable(model);
            add(pnlForm, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            btnSalvar.addActionListener(e -> { try { dao.salvar(new Cargo(txtNome.getText())); atualizarTabela(); txtNome.setText(""); } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); } });
            btnEditar.addActionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) {
                    String val = JOptionPane.showInputDialog(this, "Editar:", table.getValueAt(row, 1));
                    if(val!=null) { Cargo obj = new Cargo(val); obj.setId((int)table.getValueAt(row,0)); try{ dao.atualizar(obj); atualizarTabela();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}}
                }
            });
            btnExcluir.addActionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) try{ dao.deletar((int)table.getValueAt(row,0)); atualizarTabela();}catch(Exception ex){JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());}
            });
            atualizarTabela();
        }
        void atualizarTabela() { model.setRowCount(0); try { for(Cargo c : dao.listarTodos()) model.addRow(new Object[]{c.getId(), c.getNome_cargo()}); } catch(Exception ex){} }
    }
}