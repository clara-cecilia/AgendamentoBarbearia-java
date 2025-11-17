package VIEW;

import javax.swing.*;
import DAO.AgendamentoDAO;
import DTO.AgendamentoDTO;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MenuPrincipal extends javax.swing.JFrame {
    private JTable tbPesquisa;
    private JButton btnNovoAgendamento;
    private JTextField txtPesquisar;
    private JButton btnPesquisar;
    private JCheckBox cbAgendado;
    private JCheckBox cbConcluido;
    private JCheckBox cbCancelado;
    private JScrollPane jScrollPane1;
    private JPanel painel1;


    /**
     * Construtor: Onde a mágica da tela principal acontece.
     * Baseado na seção 8 do exemplo [cite: 349-390].
     */
    public MenuPrincipal() {

        // 2. DIZ À JANELA PARA USAR O SEU PAINEL PRINCIPAL
        this.setContentPane(painel1); // << MUDE "panel1" para o nome que você encontrou

        // 3. DIZ AO BOTÃO 'X' PARA FECHAR O PROGRAMA
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 4. AJUSTA O TAMANHO DA JANELA AO CONTEÚDO
        this.pack();

        // 5. CENTRALIZA A TELA
        this.setLocationRelativeTo(null);
        this.setTitle("Agenda da Barbearia");

        // Define o modelo da tabela
        configurarTabela();

        tbPesquisa.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                try {
                    // Pega os dados da linha editada
                    int id = Integer.parseInt(tbPesquisa.getValueAt(row, 0).toString());
                    String cliente = tbPesquisa.getValueAt(row, 1).toString();
                    String servico = tbPesquisa.getValueAt(row, 2).toString();

                    // Conversão de String para Timestamp (pode ser complexo se o formato for inválido)
                    Timestamp dataHora = Timestamp.valueOf(tbPesquisa.getValueAt(row, 3).toString());

                    String status = tbPesquisa.getValueAt(row, 4).toString();

                    // Cria o DTO com os novos dados
                    AgendamentoDTO obj = new AgendamentoDTO();
                    obj.setId(id);
                    obj.setNome_cliente(cliente);
                    obj.setServico(servico);
                    obj.setData_hora(dataHora);
                    obj.setStatus(status);

                    // Salva no banco
                    AgendamentoDAO dao = new AgendamentoDAO();
                    dao.alterarAgendamento(obj);

                    // Não precisa de JOptionepane aqui, fica muito chato
                    // JOptionPane.showMessageDialog(this, "Registro alterado com sucesso!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar alteração: "
                            + ex.getMessage() + "\nCertifique-se que a data está no formato YYYY-MM-DD HH:MM:SS");
                    listarAgendamentos(); // Recarrega para desfazer a edição inválida
                }
            }
        });

        // Listener para a tecla DELETE (DELETE)
        // Idêntico ao exemplo [cite: 395-398]
        tbPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    excluirRegistroSelecionado();
                }
            }
        });

        // --- AÇÕES DOS BOTÕES ---
        // Você pode criar isso clicando 2x no botão no Designer

        // Ação do botão "Novo Agendamento" (CREATE)
        btnNovoAgendamento.addActionListener(evt -> {
            new CadastrarAgendamento().setVisible(true);
        });

        // Ação do botão "Pesquisar" (READ + Filtro)
        btnPesquisar.addActionListener(evt -> {
            filtrar();
        });

        // Carrega os dados iniciais na tabela
        listarAgendamentos();
    }

    /**
     * Define as colunas da tabela e impede a edição do ID.
     * Baseado no exemplo [cite: 354-364]
     */
    private void configurarTabela() {
        tbPesquisa.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{ // Define as colunas
                        "ID", "Cliente", "Serviço", "Data e Hora", "Status"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID (coluna 0) não é editável
            }
        });
        // 1. Pega o modelo da coluna "Status"
        javax.swing.table.TableColumn statusColumn = tbPesquisa.getColumnModel().getColumn(4);

        // 2. Cria o ComboBox com as opções do seu banco de dados
        javax.swing.JComboBox<String> comboBoxStatus = new javax.swing.JComboBox<>();
        comboBoxStatus.addItem("Agendado");
        comboBoxStatus.addItem("Concluído");
        comboBoxStatus.addItem("Cancelado");

        // 3. Define o ComboBox como o editor padrão para aquela coluna
        statusColumn.setCellEditor(new javax.swing.DefaultCellEditor(comboBoxStatus));
        // Define o editor para a coluna "Data e Hora" (que está no índice 3)
        javax.swing.table.TableColumn dataColumn = tbPesquisa.getColumnModel().getColumn(3);

        // 1. Cria um JSpinner para usar como editor
        JSpinner spinnerData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd-mm-yyyy HH:mm:ss");
        spinnerData.setEditor(dateEditor);

        // 2. Cria um CellEditor customizado que usa o JSpinner
        javax.swing.DefaultCellEditor dateCellEditor = new javax.swing.DefaultCellEditor(new JCheckBox()) {

            // Este método é chamado quando você começa a editar a célula
            @Override
            public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                // O valor da tabela (value) é um Timestamp.
                // Precisamos convertê-lo para um Date para o JSpinner entender.
                if (value instanceof Timestamp) {
                    spinnerData.setValue(new Date(((Timestamp) value).getTime()));
                } else {
                    // Valor padrão se a célula for nula ou inválida
                    spinnerData.setValue(new Date());
                }

                // O importante é aqui: retornamos o *nosso* JSpinner,
                // e não o JCheckBox "falso" que passamos no construtor.
                return spinnerData;
            }

            // Este método é chamado quando você para de editar a célula
            @Override
            public Object getCellEditorValue() {
                // O valor do spinner (spinnerData.getValue()) é um Date.
                // Precisamos convertê-lo de volta para Timestamp para salvar na tabela/banco.
                Date date = (Date) spinnerData.getValue();
                return new Timestamp(date.getTime());
            }
        };

        // 3. Define o editor customizado para a coluna de Data/Hora
        dataColumn.setCellEditor(dateCellEditor);

    }

    /**
     * READ - Lista todos os agendamentos sem filtro.
     * Baseado no método 'listarExemplares' [cite: 399-417].
     */
    public void listarAgendamentos() {
        try {
            AgendamentoDAO dao = new AgendamentoDAO();
            List<AgendamentoDTO> lista = dao.listarAgendamentos(""); // Busca todos

            DefaultTableModel model = (DefaultTableModel) tbPesquisa.getModel();
            model.setRowCount(0); // Limpa a tabela

            for (AgendamentoDTO item : lista) {
                model.addRow(new Object[]{
                        item.getId(),
                        item.getNome_cliente(),
                        item.getServico(),
                        item.getData_hora(), // Timestamp será formatado pela JTable
                        item.getStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar: " + e.getMessage());
        }
    }

    /**
     * READ + Filtros - Lista com base na pesquisa e checkboxes.
     * Baseado no método 'filtrar' do exemplo [cite: 418-442].
     */
    public void filtrar() {
        try {
            AgendamentoDAO dao = new AgendamentoDAO();
            // 1. Filtra pelo nome do cliente no banco de dados
            List<AgendamentoDTO> lista = dao.listarAgendamentos(txtPesquisar.getText());

            DefaultTableModel model = (DefaultTableModel) tbPesquisa.getModel();
            model.setRowCount(0);

            // 2. Filtra pelos checkboxes (status) no Java
            for (AgendamentoDTO item : lista) {
                boolean exibir = false;

                // Se nenhum checkbox estiver marcado, exibe tudo (como no exemplo)
                if (!cbAgendado.isSelected() && !cbConcluido.isSelected() && !cbCancelado.isSelected()) {
                    exibir = true;
                } else {
                    // Verifica os status
                    if (cbAgendado.isSelected() && item.getStatus().equalsIgnoreCase("Agendado")) exibir = true;
                    if (cbConcluido.isSelected() && item.getStatus().equalsIgnoreCase("Concluído")) exibir = true;
                    if (cbCancelado.isSelected() && item.getStatus().equalsIgnoreCase("Cancelado")) exibir = true;
                }

                if (exibir) {
                    model.addRow(new Object[]{
                            item.getId(),
                            item.getNome_cliente(),
                            item.getServico(),
                            item.getData_hora(),
                            item.getStatus()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar: " + e.getMessage());
        }
    }

    /**
     * DELETE - Exclui a linha selecionada com a tecla Delete.
     * Baseado no método 'excluirRegistroSelecionado' [cite: 443-470].
     */
    private void excluirRegistroSelecionado() {
        int selectedRow = tbPesquisa.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(tbPesquisa.getValueAt(selectedRow, 0).toString());
            String cliente = tbPesquisa.getValueAt(selectedRow, 1).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir o agendamento de \"" + cliente + "\"?",
                    "Confirmação de exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                AgendamentoDAO dao = new AgendamentoDAO();
                dao.excluirAgendamento(id);
                // JOptionPane.showMessageDialog(this, "Agendamento de \"" + cliente + "\" excluído com sucesso!");
                listarAgendamentos(); // Atualiza a tabela
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }
}