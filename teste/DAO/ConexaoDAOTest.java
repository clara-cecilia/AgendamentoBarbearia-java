package DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class ConexaoDAOTest {

    @Test
    public void testeConexaoBancoDeDados() {
        // Ação: Tentar conectar
        ConexaoDAO conexaoDAO = new ConexaoDAO();
        Connection conn = conexaoDAO.conectaBD();

        // Verificação: A conexão não pode ser nula
        Assertions.assertNotNull(conn, "A conexão com o banco de dados falhou (retornou null). Verifique se o MySQL está rodando.");

        // Tenta fechar para não deixar conexões abertas
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}