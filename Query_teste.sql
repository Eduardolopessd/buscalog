-- =======================================================
-- SCRIPT DE CRIAÇÃO DE BANCO DE DADOS E TABELA DE LOGS
-- E INSERÇÃO DE DADOS DE EXEMPLO PARA TESTE DA APLICAÇÃO JAVA
-- =======================================================

-- 1. Criação do Banco de Dados (se não existir)
--    IMPORTANTE: O nome do banco de dados deve ser o mesmo configurado no seu DatabaseConnection.java
--    Ex: 'SeuBancoDeDadosLocal' ou 'ConsultaLogsDB'
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'ConsultaLogsDB')
BEGIN
    CREATE DATABASE ConsultaLogsDB;
    PRINT 'Banco de dados ConsultaLogsDB criado com sucesso!';
END
ELSE
BEGIN
    PRINT 'Banco de dados ConsultaLogsDB já existe. Ignorando criação.';
END
GO

-- 2. Seleciona o Banco de Dados para Uso
USE ConsultaLogsDB;
GO

-- 3. Criação da Tabela de Logs (se não existir)
--    As colunas devem corresponder aos atributos da sua classe LogEntry.java
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Logs')
BEGIN
    CREATE TABLE Logs (
        id INT IDENTITY(1,1) PRIMARY KEY, -- ID único e auto-incrementável
        numero_pedido VARCHAR(50) NULL,   -- Pode ser nulo se nem todo log tiver um pedido
        nota_fiscal VARCHAR(50) NULL,    -- Pode ser nulo se nem todo log tiver uma NF
        usuario VARCHAR(100) NOT NULL,   -- Usuário que gerou o log (não pode ser nulo)
        mensagem NVARCHAR(MAX) NOT NULL, -- Mensagem detalhada do log
        data_hora DATETIME2 DEFAULT GETDATE() -- Data e hora do log (usa a data/hora atual por padrão)
    );
    PRINT 'Tabela Logs criada com sucesso!';

    -- 4. Inserção de Dados de Exemplo
    --    Esses dados permitirão testar diferentes cenários de busca.
    INSERT INTO Logs (numero_pedido, nota_fiscal, usuario, mensagem, data_hora) VALUES
    ('PED001', 'NF001', 'joao.silva', 'Início do processamento do pedido', GETDATE()),
    ('PED001', 'NF001', 'joao.silva', 'Item A adicionado ao carrinho', GETDATE()),
    ('PED002', 'NF002', 'maria.souza', 'Geração de fatura para pedido PED002', GETDATE()),
    (NULL, 'NF003', 'carlos.pereira', 'Erro ao enviar e-mail de confirmação para NF003', GETDATE()),
    ('PED003', NULL, 'ana.gomes', 'Atualização de status do pedido PED003', GETDATE()),
    ('PED004', 'NF004', 'joao.silva', 'Confirmação de pagamento recebido', DATEADD(hour, -1, GETDATE())),
    ('PED005', 'NF005', 'maria.souza', 'Log de acesso ao sistema de estoque', DATEADD(day, -2, GETDATE())),
    ('PED006', NULL, 'joao.silva', 'Relatório diário gerado com sucesso', DATEADD(minute, -30, GETDATE())),
    (NULL, NULL, 'system', 'Manutenção agendada iniciada', DATEADD(hour, -5, GETDATE())),
    ('PED007', 'NF006', 'carlos.pereira', 'Processamento de estorno para NF006 concluído', GETDATE());

    PRINT 'Dados de exemplo inseridos na tabela Logs!';
END
ELSE
BEGIN
    PRINT 'Tabela Logs já existe. Ignorando criação e inserção de dados. Para inserir dados novamente, apague a tabela primeiro (DROP TABLE Logs;).';
END
GO
