package com.consultalog.dao;

import com.consultalog.model.LogEntry;
import com.consultalog.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Data Access Object (DAO) para a entidade LogEntry.
 * Responsável por todas as operações de consulta de logs no banco de dados.
 */
public class LogDAO {

    /**
     * Busca registros de log no banco de dados com base nos critérios fornecidos.
     * Os parâmetros podem ser nulos ou vazios, indicando que não devem ser usados como filtro.
     *
     * @param numeroPedido O número do pedido para filtrar (pode ser parcial, usa LIKE).
     * @param notaFiscal A nota fiscal para filtrar (pode ser parcial, usa LIKE).
     * @param usuario O usuário para filtrar (pode ser parcial, usa LIKE).
     * @return Uma lista de objetos LogEntry que correspondem aos critérios.
     * @throws SQLException Se ocorrer um erro durante o acesso ao banco de dados.
     */
    public List<LogEntry> searchLogs(String numeroPedido, String notaFiscal, String usuario) throws SQLException {
        List<LogEntry> logs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection(); // Obtém uma conexão com o DB

            StringBuilder sql = new StringBuilder("SELECT id, numero_pedido, nota_fiscal, usuario, mensagem, data_hora FROM Logs WHERE 1=1");

            if (numeroPedido != null && !numeroPedido.trim().isEmpty()) {
                sql.append(" AND numero_pedido LIKE ?");
            }
            if (notaFiscal != null && !notaFiscal.trim().isEmpty()) {
                sql.append(" AND nota_fiscal LIKE ?");
            }
            if (usuario != null && !usuario.trim().isEmpty()) {
                sql.append(" AND usuario LIKE ?");
            }

            sql.append(" ORDER BY data_hora DESC");

            statement = connection.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (numeroPedido != null && !numeroPedido.trim().isEmpty()) {
                statement.setString(paramIndex++, "%" + numeroPedido + "%");
            }
            if (notaFiscal != null && !notaFiscal.trim().isEmpty()) {
                statement.setString(paramIndex++, "%" + notaFiscal + "%");
            }
            if (usuario != null && !usuario.trim().isEmpty()) {
                statement.setString(paramIndex++, "%" + usuario + "%");
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dbNumeroPedido = resultSet.getString("numero_pedido");
                String dbNotaFiscal = resultSet.getString("nota_fiscal");
                String dbUsuario = resultSet.getString("usuario");
                String mensagem = resultSet.getString("mensagem");
                LocalDateTime dataHora = resultSet.getTimestamp("data_hora").toLocalDateTime();

                logs.add(new LogEntry(id, dbNumeroPedido, dbNotaFiscal, dbUsuario, mensagem, dataHora));
            }
        } finally {
            // --- BLOCO FINALLY CORRIGIDO ---
            // É crucial fechar ResultSet, Statement e Connection na ordem inversa de abertura
            // e dentro de blocos try-catch individuais para garantir que todos sejam fechados,
            // mesmo se um deles falhar ao fechar.

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }
            // Chama o método específico da sua classe DatabaseConnection para fechar a conexão
            DatabaseConnection.closeConnection(connection);
        }
        return logs;
    }
}