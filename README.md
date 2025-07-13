# Consulta de Logs Desktop (PT-BR) / Logs Query Desktop (EN)

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/Eduardolopessd/consulta-logs-desktop.svg?style=social)](https://github.com/Eduardolopessd/consulta-logs-desktop/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/Eduardolopessd/consulta-logs-desktop.svg?style=social)](https://github.com/Eduardolopessd/consulta-logs-desktop/network/members)

---

## 🇧🇷 Português (PT-BR)

## 📄 Descrição do Projeto

Este projeto é uma aplicação desktop desenvolvida em Java Swing com Maven, projetada para consultar registros de logs armazenados em um banco de dados SQL Server. A interface intuitiva permite que os usuários pesquisem logs utilizando filtros como número do pedido, nota fiscal e usuário, facilitando a visualização e análise de eventos.

Ideal para ambientes que necessitam de uma ferramenta rápida e local para auditoria ou depuração de sistemas que geram grande volume de logs transacionais.

## ✨ Funcionalidades

* **Busca por Filtros:** Consulta logs por Número do Pedido, Nota Fiscal (NF) e Usuário.
* **Interface Gráfica Intuitiva:** Desenvolvida com Java Swing para uma experiência de usuário simples.
* **Conectividade com SQL Server:** Utiliza JDBC para interagir com o banco de dados.
* **Resultados em Tabela:** Exibe os logs encontrados em uma tabela organizada.

## 🚀 Tecnologias Utilizadas

* **Java 11+**
* **Java Swing** (para a interface gráfica)
* **Maven** (para gerenciamento de dependências e build do projeto)
* **Microsoft JDBC Driver for SQL Server**
* **SQL Server** (para o banco de dados de logs)

## 🛠️ Como Configurar e Rodar o Projeto

Siga os passos abaixo para configurar e executar a aplicação na sua máquina local ou em ambiente de servidor.

### Pré-requisitos

* **JDK (Java Development Kit) 11 ou superior**
* **Maven 3.6.0 ou superior**
* **SQL Server** (com uma instância acessível e uma base de dados configurada para os logs)
* **Git** (para clonar o repositório)

### Configuração do Banco de Dados

1.  **Crie a Base de Dados:** Certifique-se de que você tem um banco de dados SQL Server disponível. O nome padrão usado no projeto é `ConsultaLogsDB`. Se você usar outro nome, ajuste no `DatabaseConnection.java`.
2.  **Crie a Tabela de Logs:** Execute o script SQL fornecido em `setup_database.sql` no seu SQL Server Management Studio (SSMS) ou ferramenta equivalente para criar a tabela `Logs` e inserir dados de exemplo.
    * [Baixe o script `setup_database.sql` aqui](setup_database.sql)

3.  **Configuração de Credenciais:**
    No arquivo `src/main/java/com/consultalog/util/DatabaseConnection.java`, atualize as seguintes constantes com as credenciais do seu SQL Server:

    ```java
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=ConsultaLogsDB;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa"; // Seu usuário do SQL Server
    private static final String PASS = "teste123";   // Sua senha do SQL Server
    ```
    * Para testes locais, `localhost:1433` é o padrão.
    * Para um servidor em cloud, substitua `localhost` pelo IP ou hostname do servidor do banco de dados (ex: `203.0.113.45:1433`).
    * Certifique-se de que o login `sa` (ou o usuário que você está usando) está **habilitado** e tem as permissões necessárias no seu SQL Server.

### Construção do Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU_USUARIO_GITHUB/consulta-logs-desktop.git](https://github.com/SEU_USUARIO_GITHUB/consulta-logs-desktop.git)
    cd consulta-logs-desktop
    ```
    *(Lembre-se de substituir `SEU_USUARIO_GITHUB` pelo seu nome de usuário real no GitHub)*

2.  **Compile e empacote o projeto usando Maven:**
    Abra o terminal na pasta raiz do projeto (onde está o `pom.xml`) e execute:
    ```bash
    mvn clean package
    ```
    Este comando irá baixar as dependências, compilar o código e gerar um arquivo JAR executável (`.jar-with-dependencies`) na pasta `target/`.

### Executando a Aplicação

1.  **Navegue até a pasta `target/`:**
    ```bash
    cd target
    ```
2.  **Execute o JAR:**
    ```bash
    java -jar consulta-logs-desktop-1.0.0-SNAPSHOT-jar-with-dependencies.jar
    ```

    A janela da aplicação desktop "Consulta de Logs" deverá aparecer.

## 🤝 Contribuição

Sinta-se à vontade para abrir issues ou pull requests se você tiver sugestões, melhorias ou encontrar bugs. Toda contribuição é bem-vinda!

## 📜 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 🇬🇧 English (EN)

## 📄 Project Description

This is a desktop application developed in Java Swing with Maven, designed to query log records stored in a SQL Server database. Its intuitive interface allows users to search for logs using filters such as order number, invoice (NF), and user, facilitating the viewing and analysis of events.

It is ideal for environments that require a quick and local tool for auditing or debugging systems that generate a high volume of transactional logs.

## ✨ Features

* **Filter-based Search:** Query logs by Order Number, Invoice (NF), and User.
* **Intuitive Graphical Interface:** Developed with Java Swing for a simple user experience.
* **SQL Server Connectivity:** Uses JDBC to interact with the database.
* **Tabled Results:** Displays found logs in an organized table format.

## 🚀 Technologies Used

* **Java 11+**
* **Java Swing** (for the graphical interface)
* **Maven** (for dependency management and project build)
* **Microsoft JDBC Driver for SQL Server**
* **SQL Server** (for the logs database)

## 🛠️ How to Set Up and Run the Project

Follow the steps below to set up and run the application on your local machine or in a server environment.

### Prerequisites

* **JDK (Java Development Kit) 11 or higher**
* **Maven 3.6.0 or higher**
* **SQL Server** (with an accessible instance and a database configured for logs)
* **Git** (to clone the repository)

### Database Configuration

1.  **Create the Database:** Ensure you have a SQL Server database available. The default name used in the project is `ConsultaLogsDB`. If you use a different name, adjust it in `DatabaseConnection.java`.
2.  **Create the Logs Table:** Execute the SQL script provided in `setup_database.sql` in your SQL Server Management Studio (SSMS) or equivalent tool to create the `Logs` table and insert sample data.
    * [Download the `setup_database.sql` script here](setup_database.sql)

3.  **Credential Configuration:**
    In the `src/main/java/com/consultalog/util/DatabaseConnection.java` file, update the following constants with your SQL Server credentials:

    ```java
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=ConsultaLogsDB;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa"; // Your SQL Server username
    private static final String PASS = "teste123";   // Your SQL Server password
    ```
    * For local testing, `localhost:1433` is the default.
    * For a cloud server, replace `localhost` with the IP address or hostname of the database server (e.g., `203.0.113.45:1433`).
    * Ensure that the `sa` login (or the user you are using) is **enabled** and has the necessary permissions in your SQL Server.

### Building the Project

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YOUR_GITHUB_USERNAME/consulta-logs-desktop.git](https://github.com/YOUR_GITHUB_USERNAME/consulta-logs-desktop.git)
    cd consulta-logs-desktop
    ```
    *(Remember to replace `YOUR_GITHUB_USERNAME` with your actual GitHub username)*

2.  **Compile and package the project using Maven:**
    Open your terminal in the project's root directory (where `pom.xml` is located) and execute:
    ```bash
    mvn clean package
    ```
    This command will download dependencies, compile your code, and generate an executable JAR file (`.jar-with-dependencies`) in the `target/` folder.

### Running the Application

1.  **Navigate to the `target/` folder:**
    ```bash
    cd target
    ```
2.  **Execute the JAR:**
    ```bash
    java -jar consulta-logs-desktop-1.0.0-SNAPSHOT-jar-with-dependencies.jar
    ```

    The "Logs Query Desktop" application window should appear.

## 🤝 Contributing

Feel free to open issues or pull requests if you have suggestions, improvements, or find bugs. All contributions are welcome!

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
