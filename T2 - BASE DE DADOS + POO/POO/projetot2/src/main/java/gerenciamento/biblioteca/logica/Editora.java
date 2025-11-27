package gerenciamento.biblioteca.logica;

public class Editora {
    private int id;
    private String nome_editora;
// ------------------------------------
    public Editora() {}

    public Editora(String nome_editora) {
        this.nome_editora = nome_editora;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setNome_editora(String nome_editora) {
        this.nome_editora = nome_editora;
    }
    public String getNome_editora() {
        return nome_editora;
    }
// ------------------------------------
}
