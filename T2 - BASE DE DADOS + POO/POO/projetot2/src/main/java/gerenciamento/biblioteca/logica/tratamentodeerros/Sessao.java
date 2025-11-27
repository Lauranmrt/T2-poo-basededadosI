package gerenciamento.biblioteca.logica.tratamentodeerros;

import gerenciamento.biblioteca.logica.Funcionario;

public class Sessao {
    private static Funcionario funcionarioLogado;

    public static void setFuncionarioLogado(Funcionario f) {
        funcionarioLogado = f;

        if(f != null) {
            System.out.println("Sessão iniciada para ID: " + f.getId() + " - Login: " + f.getLogin());
        }
    }

    public static Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }
}