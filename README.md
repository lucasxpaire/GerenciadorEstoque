# Sistema de Gestão de Estoque

Uma aplicação desktop robusta desenvolvida em Java e JavaFX para a gestão completa de uma loja. O sistema permite o controlo de stock, gestão de vendas, administração de clientes e funcionários, programas de fidelização e geração de relatórios detalhados em PDF.

## 📋 Funcionalidades

O sistema possui diferentes níveis de acesso (Proprietário e Funcionário) e abrange os seguintes módulos:

### 1\. Gestão de Estoque

  * Visualização em tempo real dos itens em estoque.
  * Adicionar novos produtos, editar informações e remover itens.
  * Registar a compra de mais quantidade de produtos existentes.

### 2\. Transações e Vendas

  * Realização de vendas selecionando produtos, clientes e funcionários.
  * Cálculo automático de totais.
  * **Sistema de Fidelização:** Verificação e utilização de pontos de fidelidade para descontos.
  * Aplicação de cupões de desconto e descontos automáticos (ex: primeira compra).
  * Registo de demandas (produtos procurados mas indisponíveis).

### 3\. Gestão de Entidades

  * **Clientes:** Cadastro, edição e remoção de clientes com registo de CPF.
  * **Funcionários:** Gestão de equipa com atribuição de credenciais de acesso (utilizador/palavra-passe) e funções.
  * **Descontos:** Criação e configuração de regras de desconto baseadas em pontos mínimos.

### 4\. Painel Administrativo (Dashboard)

  * Visão geral do negócio com gráficos interativos:
      * Distribuição de estoque (Gráfico Circular).
      * Produtos com maior movimentação (Gráfico de Barras).
      * Vendas semanais (Gráfico de Linhas).
      * Total de vendas em valor monetário.

### 5\. Relatórios

  * Geração de relatórios exportáveis em **PDF** utilizando a biblioteca iTextPDF.
  * Tipos de relatórios disponíveis:
      * Transações (Vendas e Compras).
      * Produtos mais e menos demandados.
      * Utilização de descontos.
      * Métricas de produtos mais vendidos e comprados.

-----

## 🛠️ Tecnologias Utilizadas

  * **Linguagem:** Java 21.
  * **Interface Gráfica:** JavaFX 21.
  * **Base de Dados:** PostgreSQL.
  * **Gestão de Dependências:** Apache Maven.
  * **Geração de Relatórios:** iTextPDF 7.
  * **Driver JDBC:** PostgreSQL JDBC Driver (v42.7.4).

-----

## ⚙️ Configuração e Instalação

### Pré-requisitos

1.  Ter o **Java JDK 21** instalado.
2.  Ter o **Maven** instalado.
3.  Ter o **PostgreSQL** instalado e em execução.

### 1\. Configuração da Base de Dados

O sistema requer uma base de dados PostgreSQL chamada `estoque`. Siga os passos abaixo:

1.  Crie a base de dados:
    ```sql
    CREATE DATABASE estoque;
    ```
2.  Execute o script SQL fornecido no ficheiro `BancoDeDadosEstoque.sql` (ou use a estrutura descrita em `tabelas.txt`) para criar as tabelas necessárias (`Produto`, `Estoque`, `Cliente`, `Funcionario`, `Transacao`, etc.).

### 2\. Configuração da Conexão

Antes de iniciar a aplicação, verifique as credenciais de acesso à base de dados no ficheiro `src/main/java/com/gerencia/estoque/dao/Database.java`.

Edite as constantes para corresponderem ao seu ambiente local (porta, utilizador e palavra-passe):

```java
// Arquivo: src/main/java/com/gerencia/estoque/dao/Database.java
private static final String URL = "jdbc:postgresql://localhost:5433/estoque"; // Verifique a porta (5432 ou 5433)
private static final String USER = "postgres";
private static final String PASSWORD = "sua_senha_aqui"; 
```

### 3\. Compilação e Execução

No terminal, dentro da pasta raiz do projeto:

```bash
# Compilar o projeto e descarregar as dependências
mvn clean install

# Executar a aplicação via plugin JavaFX
mvn javafx:run
```

-----

## 🔐 Acesso ao Sistema

Ao iniciar a aplicação pela primeira vez, se não existirem credenciais configuradas, o sistema criará automaticamente um utilizador proprietário padrão:

  * **Utilizador:** `prop`
  * **Palavra-passe:** `prop123`

Utilize estas credenciais no ecrã de login para aceder ao Painel do Proprietário.

-----

## 🗄️ Estrutura da Base de Dados

O sistema utiliza as seguintes tabelas principais:

  * `Credenciais` & `Funcionario`: Autenticação e dados de staff.
  * `Produto` & `Estoque`: Catálogo de itens e quantidades disponíveis.
  * `Cliente` & `Fidelidade`: Dados pessoais e sistema de pontos.
  * `Transacao`: Histórico de vendas e compras.
  * `Desconto`: Regras de descontos aplicáveis.
  * `Demanda` & `ItemDemandado`: Registo de produtos procurados.

-----

## ✒️ Autores

  * **Lucas**
