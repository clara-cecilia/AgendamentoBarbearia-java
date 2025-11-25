package VIEW;

import DTO.AgendamentoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.lang.reflect.Field;

public class CadastrarAgendamentoTest {

    @Test
    public void testeCarregamentoOpcoesServico() {
        Assertions.assertDoesNotThrow(() -> {
            CadastrarAgendamento tela = new CadastrarAgendamento();

            // --- USO DE REFLECTION PARA ACESSAR CAMPO PRIVADO ---
            // 1. Pega o campo 'cbServico' da classe
            Field campoCombo = CadastrarAgendamento.class.getDeclaredField("cbServico");

            // 2. Permite acesso a ele (pois é private)
            campoCombo.setAccessible(true);

            // 3. Pega o objeto real que está dentro da tela instanciada
            JComboBox<?> cbServico = (JComboBox<?>) campoCombo.get(tela);

            // --- VERIFICAÇÕES ---
            // Verifica se o combo não é nulo
            Assertions.assertNotNull(cbServico, "O ComboBox de serviços não foi inicializado.");

            // Verifica se tem 4 itens (Corte, Barba, Corte e Barba, Outro)
            Assertions.assertEquals(4, cbServico.getItemCount(), "Deveria haver 4 opções de serviço.");

            // Verifica se o primeiro item é "Corte"
            Assertions.assertEquals("Corte", cbServico.getItemAt(0), "A primeira opção deveria ser 'Corte'.");

            tela.dispose();
        });
    }

    @Test
    public void testeValidacaoClienteObrigatorio() {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setNome_cliente(""); // Nome vazio

        Assertions.assertFalse(dto.isValido(), "O DTO não deveria ser válido sem nome de cliente");
    }
}