package gerenciamento.biblioteca.logica.state;

import gerenciamento.biblioteca.logica.Exemplar;

public class DisponivelState implements ExemplarState {
    @Override
    public void emprestar(Exemplar exemplar) {
        // lógica está na Procedure, apenas atualiza
        exemplar.setStatus_livro("EMPRESTADO");
        System.out.println("Estado alterado para: EMPRESTADO");
    }

    @Override
    public void devolver(Exemplar exemplar) throws Exception {
        throw new Exception("O livro já está disponível. Não pode ser devolvido.");
    }
}
