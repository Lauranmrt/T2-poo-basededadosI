package gerenciamento.biblioteca.logica;

public class Obra {
    private int id;
    private String titulo;
    private Autor autor_id;
    private Editora editora_id;
    private Genero genero_id;
    private Short ano_lancamento;
    private String isbn;
// ------------------------------------
    public Obra() {}

    public Obra(String titulo, Autor autor_id, Editora editora_id, Genero genero_id, 
                                                        Short ano_lancamento, String isbn) {
        this.titulo = titulo;
        this.autor_id = autor_id;
        this.editora_id = editora_id;
        this.genero_id = genero_id;
        this.ano_lancamento = ano_lancamento;
        this.isbn = isbn;
    }
// ------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
// ------------------------------------
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
// ------------------------------------
    public void setAutor_id(Autor autor_id) {
        this.autor_id = autor_id;
    }

    public Autor getAutor_id() {
        return autor_id;
    }
// ------------------------------------
    public void setEditora_id(Editora editora_id) {
        this.editora_id = editora_id;
    }

    public Editora getEditora_id() {
        return editora_id;
    }
// ------------------------------------
    public void setGenero_id(Genero genero_id) {
        this.genero_id = genero_id;
    }

    public Genero getGenero_id() {
        return genero_id;
    }
// ------------------------------------
    public void setAno_lancamento(Short ano_lancamento) {
        this.ano_lancamento = ano_lancamento;
    }

    public Short getAno_lancamento() {
        return ano_lancamento;
    }
// ------------------------------------
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
// ------------------------------------

}
