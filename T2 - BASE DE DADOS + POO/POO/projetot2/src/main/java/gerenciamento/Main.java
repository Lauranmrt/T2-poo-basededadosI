package gerenciamento;

import gerenciamento.bibliotecadb.ConexaoBanco;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("1. Instanciando a Factory...");
        ConexaoBanco factory = new ConexaoBanco();

        System.out.println("2. Tentando abrir a conexão...");
        
        try (Connection conn = factory.getConexao()) {
            
            System.out.println("----------------------------------------");
            System.out.println("✅ SUCESSO! Conexão realizada.");
            System.out.println("----------------------------------------");
            
            System.out.println("Banco: " + conn.getMetaData().getDatabaseProductName());
            
        } catch (SQLException e) {
            System.out.println("----------------------------------------");
            System.out.println("❌ ERRO! Não foi possível conectar.");
            System.out.println("----------------------------------------");
            System.out.println("Mensagem do erro: " + e.getMessage());
            
            e.printStackTrace();
        }
    }
}
