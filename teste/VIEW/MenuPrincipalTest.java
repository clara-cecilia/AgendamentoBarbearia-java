package VIEW;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.swing.SwingUtilities;

public class MenuPrincipalTest {

    @Test
    public void testeInicializacaoMenuPrincipal() {
        // Executa na Thread de eventos do Swing para evitar erros de Thread
        SwingUtilities.invokeLater(() -> {
            Assertions.assertDoesNotThrow(() -> {
                // Tenta criar a janela
                MenuPrincipal menu = new MenuPrincipal();

                // Verifica se foi criada
                Assertions.assertNotNull(menu, "A janela MenuPrincipal deveria ter sido instanciada.");

                // Verifica se o título está correto (você definiu isso no construtor)
                Assertions.assertEquals("Agenda da Barbearia", menu.getTitle(), "O título da janela está incorreto.");

                // Fecha a janela para não ficar aberta no fundo consumindo memória
                menu.dispose();
            }, "A inicialização da janela MenuPrincipal falhou (verifique a conexão com o banco).");
        });
    }
}