package com.consultalog.model;

import java.time.LocalDateTime;

/**
 * Classe de modelo que representa um registro de log.
 * Os atributos desta classe devem corresponder às colunas da sua tabela de logs no SQL Server.
 * Os campos são declarados como 'final' para tornar o objeto imutável após sua criação.
 */
public class LogEntry {
    private final int id;                 // ID único do registro de log
    private final String numeroPedido;    // Número do pedido associado ao log
    private final String notaFiscal;      // Nota fiscal associada ao log
    private final String usuario;         // Usuário que gerou o log
    private final String mensagem;        // Mensagem detalhada do log
    private final LocalDateTime dataHora; // Data e hora em que o log foi registrado

    /**
     * Construtor completo para criar uma nova instância de LogEntry.
     * Todos os campos são inicializados aqui, garantindo a imutabilidade.
     * @param id O ID do log.
     * @param numeroPedido O número do pedido.
     * @param notaFiscal A nota fiscal.
     * @param usuario O usuário.
     * @param mensagem A mensagem do log.
     * @param dataHora A data e hora do log.
     */
    public LogEntry(final int id, final String numeroPedido, final String notaFiscal,
                    final String usuario, final String mensagem, final LocalDateTime dataHora) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.notaFiscal = notaFiscal;
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    // --- Métodos Getters ---
    // Fornecem acesso de leitura aos atributos da classe.
    // Setters não são necessários para objetos imutáveis, pois os valores não mudam.

    public int getId() {
        return id;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Sobrescreve o método toString() para fornecer uma representação textual amigável do objeto LogEntry.
     * Útil para depuração e exibição simples no console.
     * @return Uma string formatada com os detalhes do log.
     */
    @Override
    public String toString() {
        return "LogEntry{" +
               "id=" + id +
               ", numeroPedido='" + numeroPedido + '\'' +
               ", notaFiscal='" + notaFiscal + '\'' +
               ", usuario='" + usuario + '\'' +
               ", mensagem='" + mensagem + '\'' +
               ", dataHora=" + dataHora +
               '}';
    }
}