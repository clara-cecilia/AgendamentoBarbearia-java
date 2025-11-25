package DAO;

import DTO.AgendamentoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoDAOTest {

    @Test
    public void testeCadastrarEListarAgendamento() {
        // 1. Preparação (Arrange)
        AgendamentoDAO dao = new AgendamentoDAO();
        AgendamentoDTO novoAgendamento = new AgendamentoDTO();

        String nomeUnico = "TesteJUnit_" + System.currentTimeMillis(); // Nome único para não confundir
        novoAgendamento.setNome_cliente(nomeUnico);
        novoAgendamento.setServico("Corte");
        novoAgendamento.setData_hora(Timestamp.valueOf(LocalDateTime.now()));
        novoAgendamento.setStatus("Agendado");

        // 2. Ação (Act) - Cadastra no banco
        dao.cadastrarAgendamento(novoAgendamento);

        // 3. Verificação (Assert) - Busca no banco para ver se salvou
        List<AgendamentoDTO> lista = dao.listarAgendamentos(nomeUnico);

        Assertions.assertFalse(lista.isEmpty(), "A lista não deveria estar vazia após cadastrar.");
        Assertions.assertEquals(nomeUnico, lista.get(0).getNome_cliente(), "O cliente recuperado deve ser o mesmo cadastrado.");

        // Limpeza (Opcional): Excluir o dado de teste para não sujar o banco
        // dao.excluirAgendamento(lista.get(0).getId());
    }
}