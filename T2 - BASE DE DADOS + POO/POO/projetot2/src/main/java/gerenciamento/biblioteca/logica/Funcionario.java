package gerenciamento.biblioteca.logica;

public class Funcionario {
    private int id;
    private Usuario usuario_id;
    private Cargo cargo_id;
    private Double salario;
    private String login;
    private String senha;
// ------------------------------------
    public Funcionario() {}

    public Funcionario(Usuario usuario_id, Cargo cargo_id, Double salario, String login, String senha) {
        this.usuario_id = usuario_id;
        this.cargo_id = cargo_id;
        this.salario = salario;
        this.login = login;
        this.senha = senha;
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
    public void setCargo_id(Cargo cargo_id) {
        this.cargo_id = cargo_id;
    }
    public Cargo getCargo_id() {
        return cargo_id;
    }
// ------------------------------------
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
// ------------------------------------
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return senha;
    }
// ------------------------------------
    public void setSalario(Double salario) {
        this.salario = salario;
    }
    public Double getSalario() {
        return salario;
    }
// ------------------------------------
}
