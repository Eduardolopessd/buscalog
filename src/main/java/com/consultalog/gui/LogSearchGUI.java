package com.consultalog.gui;

import com.consultalog.dao.LogDAO;
import com.consultalog.model.LogEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter; // Para formatar LocalDateTime
import java.util.List;

/**
 * Classe que constrói a interface gráfica para a consulta de logs.
 * Estende JFrame para criar a janela principal da aplicação.
 */
public class LogSearchGUI extends JFrame {

    // Componentes da interface
    private JTextField numeroPedidoField;
    private JTextField notaFiscalField;
    private JTextField usuarioField;
    private JButton searchButton;
    private final JTable logTable;
    private final DefaultTableModel tableModel; // Modelo de dados para a JTable
    private LogDAO logDAO;               // Instância do DAO para acesso ao banco de dados

    /**
     * Construtor da interface gráfica.
     * Configura a janela, os painéis, campos de entrada, botão e a tabela de resultados.
     */
    public LogSearchGUI() {
        super("Consulta de Logs - Aplicação Desktop"); // Define o título da janela

        logDAO = new LogDAO(); // Inicializa o DAO

        // --- Configurações da Janela Principal ---
        setSize(800, 600); // Define o tamanho inicial da janela (largura, altura)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define o que acontece ao fechar a janela (encerra a aplicação)
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(true); // Permite redimensionar a janela

        // --- Painel Principal com BorderLayout ---
        // BorderLayout divide o contêiner em cinco regiões: NORTH, SOUTH, EAST, WEST, CENTER.
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel); // Adiciona o painel principal à janela

        // --- Painel de Entrada de Filtros (Norte) ---
        // GridLayout organiza componentes em uma grade (linhas x colunas).
        // (4 linhas, 2 colunas, 5px de espaçamento horizontal, 5px de espaçamento vertical)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        // Adiciona uma margem interna para os componentes do painel.
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Rótulos e campos de texto para os filtros
        inputPanel.add(new JLabel("Número do Pedido:"));
        numeroPedidoField = new JTextField(20); // 20 colunas visíveis
        inputPanel.add(numeroPedidoField);

        inputPanel.add(new JLabel("Nota Fiscal:"));
        notaFiscalField = new JTextField(20);
        inputPanel.add(notaFiscalField);

        inputPanel.add(new JLabel("Usuário:"));
        usuarioField = new JTextField(20);
        inputPanel.add(usuarioField);

        // Botão de busca
        searchButton = new JButton("Buscar Logs");
        inputPanel.add(searchButton);
        inputPanel.add(new JLabel("")); // Adiciona um JLabel vazio para ocupar o espaço e alinhar o botão

        // Adiciona o painel de entrada na região NORTH do mainPanel
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // --- Tabela de Resultados (Centro) ---
        // Define os nomes das colunas da tabela
        String[] columnNames = {"ID", "Nº Pedido", "NF", "Usuário", "Mensagem", "Data/Hora"};
        // Cria um DefaultTableModel, que é o modelo de dados para a JTable.
        // O 0 indica que inicialmente não há linhas.
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Sobrescreve para tornar todas as células da tabela não editáveis
            }
        };
        logTable = new JTable(tableModel); // Cria a JTable com o modelo definido
        logTable.setFillsViewportHeight(true); // Faz a tabela preencher a altura disponível no JScrollPane

        // Adiciona a tabela a um JScrollPane para que ela tenha barras de rolagem se o conteúdo exceder.
        JScrollPane scrollPane = new JScrollPane(logTable);
        // Adiciona o JScrollPane (que contém a tabela) na região CENTER do mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Configuração do Listener para o Botão de Busca ---
        // Adiciona um ActionListener ao botão. Quando o botão é clicado, o método actionPerformed é executado.
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(); // Chama o método que executa a lógica de busca
            }
        });
    }

    /**
     * Executa a lógica de busca de logs, obtendo os valores dos campos de filtro,
     * chamando o DAO e atualizando a tabela com os resultados.
     */
    private void performSearch() {
        // Obtém o texto dos campos de entrada e remove espaços em branco no início/fim.
        String numeroPedido = numeroPedidoField.getText().trim();
        String notaFiscal = notaFiscalField.getText().trim();
        String usuario = usuarioField.getText().trim();

        // Limpa todas as linhas existentes na tabela antes de adicionar novos resultados.
        tableModel.setRowCount(0);

        try {
            // Chama o método searchLogs do DAO.
            // Se um campo de filtro estiver vazio, passa 'null' para que o DAO não filtre por ele.
            List<LogEntry> results = logDAO.searchLogs(
                    numeroPedido.isEmpty() ? null : numeroPedido,
                    notaFiscal.isEmpty() ? null : notaFiscal,
                    usuario.isEmpty() ? null : usuario
            );

            // Verifica se algum resultado foi encontrado
            if (results.isEmpty()) {
                // Exibe uma mensagem informativa se nenhum log for encontrado
                JOptionPane.showMessageDialog(this, "Nenhum log encontrado com os critérios fornecidos.",
                        "Resultados da Busca", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Formata a data e hora para exibição na tabela
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                // Itera sobre a lista de LogEntry e adiciona cada log como uma nova linha na tabela
                for (LogEntry log : results) {
                    Object[] rowData = {
                            log.getId(),
                            log.getNumeroPedido(),
                            log.getNotaFiscal(),
                            log.getUsuario(),
                            log.getMensagem(),
                            log.getDataHora().format(formatter) // Formata a data e hora
                    };
                    tableModel.addRow(rowData); // Adiciona a nova linha ao modelo da tabela
                }
            }
        } catch (SQLException ex) {
            // Em caso de erro no banco de dados, exibe uma mensagem de erro ao usuário.
            JOptionPane.showMessageDialog(this,
                    "Erro ao consultar o banco de dados: " + ex.getMessage(),
                    "Erro de Banco de Dados",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Imprime o stack trace completo no console para depuração.
        }
    }
}