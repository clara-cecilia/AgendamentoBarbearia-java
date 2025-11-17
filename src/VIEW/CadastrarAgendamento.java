package VIEW;

import javax.swing.*;
import DAO.AgendamentoDAO; // Nosso DAO
import DTO.AgendamentoDTO; // Nosso DTO
import javax.swing.JOptionPane;
import java.sql.Timestamp; // Para converter a data/hora

public class CadastrarAgendamento extends javax.swing.JFrame{
    private JTextField txtCliente;
    private JComboBox cbServico;
    private JTextField txtDataHora;
    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JPanel painel2;

    /**
     * Construtor
     */
    public CadastrarAgendamento() {
        // 2. DIZ À JANELA PARA USAR O SEU PAINEL
        // (você nomeou corretamente "painel2" na sua variável)
        this.setContentPane(painel2);

        // 3. IMPORTANTE: FAZ O 'X' FECHAR SÓ ESTA JANELA, NÃO O APP INTEIRO
        // (Diferente do MenuPrincipal, que usa EXIT_ON_CLOSE)
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 4. AJUSTA O TAMANHO DA JANELA AO CONTEÚDO
        this.pack();

        // 5. CENTRALIZA A TELA E DEFINE O TÍTULO
        this.setLocationRelativeTo(null);
        this.setTitle("Novo Agendamento");


        // Vamos popular o ComboBox de Serviços
        cbServico.addItem("Corte");
        cbServico.addItem("Barba");
        cbServico.addItem("Corte e Barba");
        cbServico.addItem("Outro");

        // Ação do botão CADASTRAR
        btnCadastrar.addActionListener(evt -> {
            cadastrar();
        });

        // Ação do botão LIMPAR
        // Idêntico ao exemplo [cite: 321-326]
        btnLimpar.addActionListener(evt -> {
            limparCampos();
        });
    }

    /**
     * Ação de Cadastrar (CREATE)
     * Baseado no 'BtnCadastrarActionPerformed' do exemplo [cite: 308-320].
     */
    private void cadastrar() {
        // --- VALIDAÇÃO DE CAMPOS ---
        // Este é um requisito obrigatório da atividade (0,5 pontos)

        if (txtCliente.getText().isEmpty() || txtDataHora.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cliente e Data/Hora são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return; // Para a execução
        }

        AgendamentoDTO objagendamento = new AgendamentoDTO();
        objagendamento.setNome_cliente(txtCliente.getText());
        objagendamento.setServico((String) cbServico.getSelectedItem());

        // Converter a String de data/hora para Timestamp
        try {
            // Exige formato: YYYY-MM-DD HH:MM:SS
            String dataHoraTexto = txtDataHora.getText();
            if (!dataHoraTexto.contains(" ")) {
                dataHoraTexto += " 00:00:00"; // Adiciona hora padrão se não houver
            }
            objagendamento.setData_hora(Timestamp.valueOf(dataHoraTexto));

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de Data e Hora inválido.\nUse: YYYY-MM-DD HH:MM:SS",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return; // Para a execução
        }

        // Enviar para o DAO
        try {
            AgendamentoDAO dao = new AgendamentoDAO();
            dao.cadastrarAgendamento(objagendamento);

            JOptionPane.showMessageDialog(this, "Agendamento cadastrado com sucesso!");
            limparCampos();
            this.dispose(); // Fecha esta tela de cadastro

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    /**
     * Ação de Limpar
     * Baseado no 'BtnLimparActionPerformed' [cite: 321-326].
     */
    private void limparCampos() {
        txtCliente.setText("");
        txtDataHora.setText("");
        cbServico.setSelectedIndex(0); // Volta para "Corte"
        txtCliente.requestFocus(); // Foca no campo de cliente
    }


}
