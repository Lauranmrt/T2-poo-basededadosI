package gerenciamento.biblioteca.logica.state;

import gerenciamento.biblioteca.logica.Exemplar;

public class EmprestadoState implements ExemplarState {
    @Override
    public void emprestar(Exemplar exemplar) throws Exception {
        throw new Exception("Livro já emprestado. Não pode ser emprestado novamente.");
    }

    @Override
    public void devolver(Exemplar exemplar) {
        exemplar.setStatus_livro("DISPONIVEL");
        System.out.println("Estado alterado para: DISPONIVEL");
    }
}