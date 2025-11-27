package gerenciamento.bibliotecadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    public Connection getConexao() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gerenciamento_de_biblioteca",
                "root", 
                "Laura159753"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco", e);
        }
    }
}
