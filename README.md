# 🎬 ScreenScore API

> API para gerenciamento e avaliação de filmes construída com **Java** e **Spring Boot**.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue.svg)](https://www.mysql.com/)
[![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-green.svg)](https://swagger.io/)
[![JDBC](https://img.shields.io/badge/JDBC-Persistence-yellow.svg)](https://docs.oracle.com/javase/tutorial/jdbc/)

---

# 📌 Sobre o projeto

O **ScreenScore** é uma API para **gerenciamento e avaliação de filmes**, permitindo que usuários cadastrem filmes, consultem informações e publiquem avaliações, criando **rankings e estatísticas dentro da plataforma**.

Uma das funcionalidades principais é a **integração com uma API externa de filmes**, que permite buscar automaticamente informações como:

- título
- data de lançamento
- sinopse
- gêneros

Isso facilita o processo de cadastro e mantém os dados padronizados dentro do sistema.

O backend segue uma **arquitetura em camadas inspirada na Arquitetura Hexagonal**, separando domínio, aplicação e infraestrutura. Essa abordagem ajuda a manter o código **mais organizado, desacoplado e preparado para evoluções futuras**.

---

# 🚀 Funcionalidades atuais

Atualmente a API possui:

- CRUD completo de filmes
- Filtros de busca por **título**
- Filtros de busca por **gênero**
- Integração com **API externa de filmes**
- Documentação automática da API com **OpenAPI / Swagger**
- Tratamento padronizado de erros seguindo **RFC 7807**

---

# 🔮 Próximas evoluções do projeto

O projeto ainda está em desenvolvimento e as próximas evoluções planejadas incluem:

- Sistema de **usuários**
- Sistema de **avaliações de filmes**
- **Rankings de filmes** baseados nas avaliações
- **Autenticação e controle de acesso** para os usuários da plataforma
- Migração da camada de persistência atual (**JDBC**) para soluções do ecossistema **Spring Data**

---

# 🛠️ Stack de Tecnologias

## Linguagem

**Java 21** — linguagem principal utilizada no desenvolvimento da aplicação.

## Framework

**Spring Boot** — framework utilizado para construção da API e gerenciamento de dependências.

## Persistência de Dados

**JDBC** — acesso ao banco de dados utilizando a API de persistência nativa do Java.

## Banco de Dados

**MySQL** — banco de dados relacional utilizado para armazenamento das informações do sistema.

## Integração com APIs

**OpenFeign** — utilizado para comunicação com API externa de filmes.

## Documentação

**OpenAPI / Swagger** — geração automática da documentação dos endpoints da API.

---

# 🏗️ Arquitetura

O projeto segue uma **arquitetura em camadas inspirada na Arquitetura Hexagonal**, separando responsabilidades entre:

### Domínio
Contém as **entidades e regras de negócio da aplicação**.

### Aplicação
Contém os **casos de uso e serviços da aplicação**, responsáveis por coordenar a lógica de negócio.

### Infraestrutura
Contém implementações externas, como:

- acesso ao banco de dados
- integrações com APIs externas
- configurações do framework

Essa separação facilita a **manutenção, evolução e testabilidade do sistema**.

---

# 🖥️ Frontend

O frontend da aplicação está sendo desenvolvido em um repositório separado.

O repositório do frontend pode ser encontrado em:

https://github.com/jonathan7gb/ScreenScore-UI

---

# 📑 Documentação da API

A documentação da API é gerada automaticamente através do **Swagger / OpenAPI**.

Ela pode ser acessada de duas formas:

### 🔗 Ambiente online (deploy)

https://screenscore-api-yrw8.onrender.com/swagger-ui/index.html

### 💻 Ambiente local

Após iniciar a aplicação localmente:

http://localhost:8080/swagger-ui/index.html
