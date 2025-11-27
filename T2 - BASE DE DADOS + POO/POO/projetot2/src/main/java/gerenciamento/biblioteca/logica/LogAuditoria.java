package gerenciamento.biblioteca.logica;

import java.time.LocalDateTime;
import org.w3c.dom.Text;

public class LogAuditoria {
    private int id;
    private String acao;
    private int usuario_id;
    private String usuario_nome;
    private int exemplar_id;
    private String titulo_exemplar;
    private LocalDateTime data_hora;
    private Text detalhes;
// ------------------------------------
    public LogAuditoria() {}

    public LogAuditoria(String acao, int usuario_id, String usuario_nome, int exemplar_id, 
                                                        String titulo_exemplar, LocalDateTime data_hora, Text detalhes) {
        this.acao = acao;
        this.usuario_id = usuario_id;
        this.usuario_nome = usuario_nome;
        this.exemplar_id = exemplar_id;
        this.titulo_exemplar = titulo_exemplar;
        this.data_hora = data_hora;
        this.detalhes = detalhes;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setAcao(String acao) {
        this.acao = acao;
    }
    public String getAcao() {
        return acao;
    }
// ------------------------------------
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    public int usuario_id() {
        return usuario_id;
    }
// ------------------------------------
    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }
    public String getUsuario_nome() {
        return usuario_nome;
    }
// ------------------------------------
    public void setExemplar_afetado_id(int exemplar_id) {
        this.exemplar_id = exemplar_id;
    }
    public int getExemplar_id() {
        return exemplar_id;
    }
// ------------------------------------
    public void setTitulo_exemplar(String titulo_exemplar) {
        this.titulo_exemplar = titulo_exemplar;
    }
    public String getTitulo_exemplar() {
        return titulo_exemplar;
    }
// ------------------------------------
    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }
    public LocalDateTime getData_hora() {
        return data_hora;
    }
// ------------------------------------
    public void setDetalhes(Text detalhes) {
        this.detalhes = detalhes;
    }
    public Text getDetalhes() {
        return detalhes;
    }
// ------------------------------------
}
