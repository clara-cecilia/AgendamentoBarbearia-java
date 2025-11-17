package DAO;

import DTO.AgendamentoDTO;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; // Usar JOptionPane para erros é melhor

public class AgendamentoDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    /**
     * CREATE - Cadastra um novo agendamento no banco de dados.
     * Baseado no método 'cadastrarExemplar' do exemplo[cite: 144].
     */
    public void cadastrarAgendamento(AgendamentoDTO objagendamento) {
        String sql = "INSERT INTO agendamentos (nome_cliente, servico, data_hora, status) VALUES (?, ?, ?, ?)";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objagendamento.getNome_cliente());
            pstm.setString(2, objagendamento.getServico());
            pstm.setTimestamp(3, objagendamento.getData_hora()); // Usamos setTimestamp
            pstm.setString(4, "Agendado"); // Status padrão ao cadastrar

            pstm.execute();
            pstm.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar agendamento: " + e.getMessage());
        }
    }

    /**
     * READ - Lista agendamentos filtrando pelo nome do cliente.
     * Baseado no método 'listarExemplares'[cite: 191].
     * Vamos ordenar pela data_hora, que faz mais sentido para uma agenda.
     */
    public List<AgendamentoDTO> listarAgendamentos(String filtroCliente) {
        String sql = "SELECT * FROM agendamentos WHERE nome_cliente LIKE ? ORDER BY data_hora ASC";
        conn = new ConexaoDAO().conectaBD();
        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + filtroCliente + "%"); // O '%' permite buscar por partes do nome
            rs = pstm.executeQuery();

            while (rs.next()) {
                AgendamentoDTO obj = new AgendamentoDTO();
                obj.setId(rs.getInt("id"));
                obj.setNome_cliente(rs.getString("nome_cliente"));
                obj.setServico(rs.getString("servico"));
                obj.setData_hora(rs.getTimestamp("data_hora")); // Usamos getTimestamp
                obj.setStatus(rs.getString("status"));

                lista.add(obj);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar agendamentos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * UPDATE - Altera um agendamento existente (usado pela tabela).
     * Baseado no método 'alterarExemplar' do exemplo[cite: 214].
     */
    public void alterarAgendamento(AgendamentoDTO objagendamento) {
        String sql = "UPDATE agendamentos SET nome_cliente = ?, servico = ?, data_hora = ?, status = ? WHERE id = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objagendamento.getNome_cliente());
            pstm.setString(2, objagendamento.getServico());
            pstm.setTimestamp(3, objagendamento.getData_hora());
            pstm.setString(4, objagendamento.getStatus());
            pstm.setInt(5, objagendamento.getId());

            pstm.execute();
            pstm.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar agendamento: " + e.getMessage());
        }
    }

    /**
     * DELETE - Exclui um agendamento pelo ID.
     * Baseado no método 'excluirExemplar'[cite: 178].
     */
    public void excluirAgendamento(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);

            pstm.execute();
            pstm.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir agendamento: " + e.getMessage());
        }
    }
}