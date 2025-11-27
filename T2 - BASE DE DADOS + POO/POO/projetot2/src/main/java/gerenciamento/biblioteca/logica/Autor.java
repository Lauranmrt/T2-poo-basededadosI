package gerenciamento.biblioteca.logica;

public class Autor {
    private int id;
    private String nome_autor;
// ------------------------------------
    public Autor() {}

    public Autor(String nome_autor) {
        this.nome_autor = nome_autor;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setNome_autor(String nome_autor) {
        this.nome_autor = nome_autor;
    }
    public String getNome_autor(){
        return nome_autor;
    }
// ------------------------------------
}
