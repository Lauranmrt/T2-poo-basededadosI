package gerenciamento.biblioteca.logica;

public class Exemplar {
    private int id;
    private Obra obra_id;
    private String status_livro;
// ------------------------------------
    public Exemplar() {}

    public Exemplar(Obra obra_id, String status_livro) {
        this.obra_id = obra_id;
        this.status_livro = status_livro;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
// ------------------------------------
    public void setObra_id(Obra obra_id) {
        this.obra_id = obra_id;
    }

    public Obra getObra_id() {
        return obra_id;
    }
// ------------------------------------
    public void setStatus_livro(String status_livro) {
        this.status_livro = status_livro;
    }

    public String getStatus_livro() {
        return status_livro;
    }
// ------------------------------------
}
