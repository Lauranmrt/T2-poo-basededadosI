package gerenciamento.biblioteca.logica;

public class Usuario {
    private int id;
    private String nome_usuario;
    private String cpf;
    private String email;
    private String telefone;
// ------------------------------------
    public Usuario() {}

    public Usuario(String nome_usuario, String cpf, String email, String telefone) {
        this.nome_usuario = nome_usuario;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
// ------------------------------------
    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }
    public String getNome_usuario() {
        return nome_usuario;
    }
// ------------------------------------
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCpf() {
        return cpf;
    }
// ------------------------------------
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
// ------------------------------------
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getTelefone() {
        return telefone;
    }
// ------------------------------------
}
