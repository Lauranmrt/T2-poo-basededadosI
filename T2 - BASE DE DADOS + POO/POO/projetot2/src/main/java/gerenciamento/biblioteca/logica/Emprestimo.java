package gerenciamento.biblioteca.logica;

import java.time.LocalDateTime;
import java.util.Date;

public class Emprestimo {
    private int id;
    private Usuario usuario_id;
    private Exemplar exemplar_id;
    private LocalDateTime data_emprestimo;
    private Date data_previsao_devolucao;
    private LocalDateTime data_devolucao_real;
    private String status_emprestimo;
// ------------------------------------
    public Emprestimo() {}

    public Emprestimo(Usuario usuario_id, Exemplar exemplar_id, LocalDateTime data_emprestimo, Date data_previsao_devolucao, 
                                                        LocalDateTime data_devolucao_real, String status_emprestimo) {
        this.usuario_id = usuario_id;
        this.exemplar_id = exemplar_id;
        this.data_emprestimo = data_emprestimo;
        this.data_previsao_devolucao = data_previsao_devolucao;
        this.data_devolucao_real = data_devolucao_real;
        this.status_emprestimo = status_emprestimo;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setUsuario_id(Usuario usuario_id) {
        this.usuario_id = usuario_id;
    }
    public Usuario getUsuario_id() {
        return usuario_id;
    }
// ------------------------------------
    public void setExemplar_id(Exemplar exemplar_id) {
        this.exemplar_id = exemplar_id;
    }
    public Exemplar getExemplar_id() {
        return exemplar_id;
    }
// ------------------------------------
    public void setData_devolucao_real(LocalDateTime data_devolucao_real) {
        this.data_devolucao_real = data_devolucao_real;
    }
    public LocalDateTime getData_devolucao_real() {
        return data_devolucao_real;
    }
// ------------------------------------
    public void setData_previsao_devolucao(Date data_previsao_devolucao) {
        this.data_previsao_devolucao = data_previsao_devolucao;
    }
    public Date getData_previsao_devolucao() {
        return data_previsao_devolucao;
    }
// ------------------------------------
    public void setData_emprestimo(LocalDateTime data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }
    public LocalDateTime getData_emprestimo() {
        return data_emprestimo;
    }
// ------------------------------------
    public void setStatus_emprestimo(String status_emprestimo) {
        this.status_emprestimo = status_emprestimo;
    }
    public String getStatus_emprestimo() {
        return status_emprestimo;
    }
// ------------------------------------
}
