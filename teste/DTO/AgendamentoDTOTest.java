package DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AgendamentoDTOTest {

    @Test
    public void testeCriacaoObjetoAgendamento() {
        // Cenário: Criando um objeto DTO
        AgendamentoDTO agendamento = new AgendamentoDTO();

        // Dados de teste
        String nomeTeste = "Cliente Teste Unitário";
        String servicoTeste = "Corte";
        String statusTeste = "Agendado";
        Timestamp dataTeste = Timestamp.valueOf(LocalDateTime.now());

        // Ação: Usando os métodos (Setters)
        agendamento.setId(1);
        agendamento.setNome_cliente(nomeTeste);
        agendamento.setServico(servicoTeste);
        agendamento.setStatus(statusTeste);
        agendamento.setData_hora(dataTeste);

        // Verificação (Asserts): Confirma se o que entrou é o que sai (Getters)
        Assertions.assertEquals(1, agendamento.getId(), "O ID deveria ser 1");
        Assertions.assertEquals(nomeTeste, agendamento.getNome_cliente(), "O nome do cliente não confere");
        Assertions.assertEquals(servicoTeste, agendamento.getServico(), "O serviço não confere");
        Assertions.assertEquals(statusTeste, agendamento.getStatus(), "O status não confere");
        Assertions.assertEquals(dataTeste, agendamento.getData_hora(), "A data não confere");
    }
}