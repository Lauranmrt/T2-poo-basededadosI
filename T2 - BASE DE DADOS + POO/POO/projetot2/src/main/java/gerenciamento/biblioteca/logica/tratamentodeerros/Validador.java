package gerenciamento.biblioteca.logica.tratamentodeerros;

import java.text.Normalizer;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Validador {

    // Remove acentos, remove espaços extras nas pontas e converte para MAIÚSCULO
    public static String tratarTexto(String texto) {
        if (texto == null) return null;
        
        // Normaliza para remover acentos
        String nfdNormalizedString = Normalizer.normalize(texto, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String semAcento = pattern.matcher(nfdNormalizedString).replaceAll("");
        
        // Retorna maiúsculo e sem espaços nas pontas
        return semAcento.toUpperCase().trim();}

    // Valida senha
    public static void validarSenha(String senha) throws Exception {
        if (senha == null || senha.contains(" ")) {
            throw new Exception("A senha não pode conter espaços em branco.");
        }
        if (senha.length() < 4) {
             throw new Exception("A senha deve ter no mínimo 4 caracteres.");
        }
    }

    // validação do CPF

    public static String validarEFormatarCPF(String cpf) throws Exception {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        
        if (cpfLimpo.length() != 11) {
            throw new Exception("CPF deve conter 11 dígitos.");
        }

        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new Exception("CPF inválido (Números repetidos).");
        }

        try {
            char dig10, dig11;
            int sm, i, r, num, peso;

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpfLimpo.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpfLimpo.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            if ((dig10 != cpfLimpo.charAt(9)) || (dig11 != cpfLimpo.charAt(10))) {
                throw new Exception("CPF Inválido: Dígitos verificadores não conferem.");
            }

        } catch (InputMismatchException erro) {
            throw new Exception("Erro ao validar CPF.");
        }

        // retorna formatado
        return cpfLimpo.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}