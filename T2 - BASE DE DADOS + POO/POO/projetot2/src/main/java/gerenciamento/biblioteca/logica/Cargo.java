package gerenciamento.biblioteca.logica;

public class Cargo {
    private int id;
    private String nome_cargo;
// ------------------------------------
    public Cargo() {}

    public Cargo(String nome_cargo) {
        this.nome_cargo = nome_cargo;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setNome_cargo(String nome_cargo)  {
        this.nome_cargo = nome_cargo;
    }
    public String getNome_cargo() {
        return nome_cargo;
    }
// ------------------------------------
}
