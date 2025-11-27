package gerenciamento.biblioteca.logica;

public class Genero {
    private int id;
    private String nome_genero;
// ------------------------------------
    public Genero() {}

    public Genero(String nome_genero) {
        this.nome_genero = nome_genero;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setNome_genero(String nome_genero) {
        this.nome_genero = nome_genero;
    }
    public String getNome_genero() {
        return nome_genero;
    }
// ------------------------------------
}
