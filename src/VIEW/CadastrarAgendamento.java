package VIEW;

import javax.swing.*;
import DAO.AgendamentoDAO; // Nosso DAO
import DTO.AgendamentoDTO; // Nosso DTO
import javax.swing.JOptionPane;
import java.sql.Timestamp; // Para converter a data/hora
import java.util.Date;

public class CadastrarAgendamento extends javax.swing.JFrame{
    private JTextField txtCliente;
    private JComboBox cbServico;
    private JSpinner spinnerDataHora;
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
        SpinnerDateModel model = new SpinnerDateModel();
        spinnerDataHora.setModel(model);
        // Define o formato de exibição
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDataHora, "dd-MM-yyyy HH:mm:ss");
        spinnerDataHora.setEditor(editor);
        // Define a data/hora atual como valor inicial
        spinnerDataHora.setValue(new Date());


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
        if (txtCliente.getText().isEmpty()) { // BASTA CHECAR O CLIENTE
            JOptionPane.showMessageDialog(this, "O nome do Cliente é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return; // Para a execução
        }

        AgendamentoDTO objagendamento = new AgendamentoDTO();
        objagendamento.setNome_cliente(txtCliente.getText());
        objagendamento.setServico((String) cbServico.getSelectedItem());

        // Converter a Date do Spinner para Timestamp
        try {
            // 1. Pega o valor do spinner (é um java.util.Date)
            Date dataSelecionada = (Date) spinnerDataHora.getValue();

            // 2. Converte para java.sql.Timestamp
            objagendamento.setData_hora(new Timestamp(dataSelecionada.getTime()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao ler a data do spinner: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
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
        spinnerDataHora.setValue(new Date()); // Reseta para data/hora atual
        cbServico.setSelectedIndex(0);
        txtCliente.requestFocus();
    }


}
