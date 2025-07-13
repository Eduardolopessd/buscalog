package com.consultalog.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária para gerenciar a conexão com o banco de dados SQL Server.
 * Contém métodos para obter e fechar conexões.
 */
public class DatabaseConnection {

    // URL de conexão com o banco de dados SQL Server.
    // Substitua 'localhost:1433' pelo endereço do seu servidor e 'SeuBancoDeDados' pelo nome do seu DB.
    // 'encrypt=false;trustServerCertificate=true;' são configurações comuns para desenvolvimento.
    // Para produção, considere configurações de segurança mais robustas (SSL/TLS).
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=SeuBancoDeDados;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa"; // Seu usuário do SQL Server
    private static final String PASS = "Teste123";   // Sua senha do SQL Server

    /**
     * Estabelece e retorna uma nova conexão com o banco de dados.
     * @return Uma instância de Connection.
     * @throws SQLException Se ocorrer um erro ao tentar conectar ao banco de dados.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Opcional: Carregar o driver JDBC explicitamente.
            // Para JDBC 4.0+ (Java 6+), o DriverManager geralmente encontra o driver automaticamente se ele estiver no classpath.
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            // Imprime o erro no console e relança a exceção para que o chamador possa tratá-la.
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Fecha uma conexão com o banco de dados, se ela não for nula.
     * @param connection A conexão a ser fechada.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Imprime o erro caso não consiga fechar a conexão.
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}