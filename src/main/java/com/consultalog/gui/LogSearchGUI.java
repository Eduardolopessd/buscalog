package com.consultalog.gui;

import com.consultalog.model.LogEntry;
import com.consultalog.dao.LogDAO;
// import com.consultalog.util.DatabaseConnection; // Não é mais necessário aqui diretamente

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.net.URL; 
    

public class LogSearchGUI extends JFrame {

    private JTextField txtNumeroPedido;
    private JTextField txtNotaFiscal;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JTable logTable;
    private DefaultTableModel tableModel;
    private LogDAO logDAO; // Instância do DAO

    public LogSearchGUI() {
        setTitle("Consulta de Logs - Aplicação Desktop");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logDAO = new LogDAO(); // Inicializa a instância do DAO aqui!

                try {
            // Caminho para o ícone dentro da pasta 'resources'
            // O caminho é relativo à raiz dos recursos ou do classpath
            URL iconURL = getClass().getClassLoader().getResource("icon.png");
            if (iconURL != null) {
                Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
                this.setIconImage(icon); // Define o ícone da janela
            } else {
                System.err.println("Ícone 'icon.png' não encontrado em src/main/resources.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar o ícone: " + e.getMessage());
            e.printStackTrace();
        }
        // --- FIM DO BLOCO DO ÍCONE ---

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        txtNumeroPedido = new JTextField(20);
        txtNotaFiscal = new JTextField(20);
        txtUsuario = new JTextField(20);
        btnBuscar = new JButton("Buscar Logs");

        String[] columnNames = {"ID", "Nº Pedido", "NF", "Usuário", "Mensagem", "Data/Hora"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        logTable = new JTable(tableModel);
        logTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        logTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        logTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        logTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        logTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        logTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        logTable.getColumnModel().getColumn(4).setPreferredWidth(350);
        logTable.getColumnModel().getColumn(5).setPreferredWidth(180);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Busca"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Número do Pedido:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        filterPanel.add(txtNumeroPedido, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        filterPanel.add(new JLabel("Nota Fiscal:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        filterPanel.add(txtNotaFiscal, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        filterPanel.add(new JLabel("Usuário:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        filterPanel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        filterPanel.add(btnBuscar, gbc);

        add(filterPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(logTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLogs();
            }
        });
    }

    private void buscarLogs() {
        String numeroPedido = txtNumeroPedido.getText().trim();
        String notaFiscal = txtNotaFiscal.getText().trim();
        String usuario = txtUsuario.getText().trim();

        if (numeroPedido.isEmpty() && notaFiscal.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Pelo menos um dos campos 'Número do Pedido' ou 'Nota Fiscal' deve ser preenchido.",
                    "Filtro Obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);

        try {
            // --- CORREÇÃO AQUI: Chame o método da instância 'logDAO' ---
            List<LogEntry> logs = logDAO.searchLogs(numeroPedido, notaFiscal, usuario);

            if (logs.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nenhum log encontrado com os filtros informados.",
                        "Resultados da Busca",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                for (LogEntry log : logs) {
                    tableModel.addRow(new Object[]{
                            log.getId(),
                            log.getNumeroPedido(),
                            log.getNotaFiscal(),
                            log.getUsuario(),
                            log.getMensagem(),
                            log.getDataHora().format(formatter)
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao consultar o banco de dados: " + ex.getMessage(),
                    "Erro de Banco de Dados",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}