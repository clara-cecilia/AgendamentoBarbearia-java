package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    public Connection conectaBD() {
        Connection conn = null;

        try {
            String url = "jdbc:mysql://localhost:3306/agendamento_barbearia?user=root&password=2904";
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            // No projeto final, é melhor usar JOptionPane para mostrar o erro
            System.out.println("Erro na conexão: " + e.getMessage());
        }

        return conn;
    }
}
