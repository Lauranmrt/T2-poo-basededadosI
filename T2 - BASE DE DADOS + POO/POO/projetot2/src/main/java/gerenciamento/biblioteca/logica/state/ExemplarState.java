package gerenciamento.biblioteca.logica.state;

import gerenciamento.biblioteca.logica.Exemplar;

public interface ExemplarState {
    void emprestar(Exemplar exemplar) throws Exception;
    void devolver(Exemplar exemplar) throws Exception;
}
