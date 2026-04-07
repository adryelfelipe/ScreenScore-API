# 🎬 ScreenScore API

> API para gerenciamento e avaliação de filmes construída com **Java** e **Spring Boot**, com foco em **consistência, desacoplamento e evolução sustentável**.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue.svg)](https://www.mysql.com/)
[![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-green.svg)](https://swagger.io/)
[![Spring Data JDBC](https://img.shields.io/badge/Spring%20Data-JDBC-yellow.svg)](https://spring.io/projects/spring-data)

---

# 📌 Sobre o projeto

O **ScreenScore** é uma API para **gestão e avaliação de filmes**, permitindo que usuários cadastrem filmes, publiquem avaliações e construam rankings dentro da plataforma.

Mais do que um CRUD, o objetivo da V1 foi construir uma base **pronta para evoluir sem gerar dívida técnica**, aplicando decisões que aumentam a **robustez e previsibilidade do sistema**.

---

# 🎬 Domínio da aplicação

- Gestão de **filmes**
- Gestão de **usuários**
- Sistema de **avaliações**
- Base preparada para **rankings e estatísticas**

---

# 🧠 Decisões de Engenharia (V1)

## 🔐 Arquitetura e Segurança

- Autenticação centralizada com **Spring AOP**
- Gerenciamento de sessão com **Spring**
- Controle de acesso desacoplado do domínio

👉 Segurança aplicada como **cross-cutting concern**, sem poluir regras de negócio

---

## 🔗 Integração com API externa

- Separação entre **domínio interno** e **domínio externo**
- Conversão de dados para evitar acoplamento
- Uso de **DTOs** para exposição controlada

👉 Integrações sem comprometer a estabilidade do sistema

---

## ☁️ Armazenamento de arquivos (AWS S3)

- Upload próprio de posters de filmes
- Persistência apenas da **chave do arquivo**
- Geração de **URLs pré-assinadas** para acesso seguro

👉 Controle total sobre dados críticos e maior resiliência

---

## ⚙️ Consistência e Manutenção

- Padronização de erros com **ProblemDetail (RFC 7807)**
- Centralização com **GlobalExceptionHandler**
- Reutilização de schemas de erro na documentação
- Migração de **JDBC → Spring Data JDBC**

👉 Menos boilerplate, mais consistência e manutenção simplificada

---

# 🚀 Funcionalidades

- CRUD completo de filmes
- CRUD de usuários
- Sistema de avaliações
- Integração com API externa de filmes
- Upload e gerenciamento de imagens (S3)
- Documentação automática com Swagger
- Tratamento padronizado de erros

---

# 🏗️ Arquitetura

O projeto segue uma abordagem inspirada em **Arquitetura Hexagonal (Ports & Adapters)**:

### Domínio
Regras de negócio e entidades centrais

### Aplicação
Casos de uso e orquestração

### Infraestrutura
- Banco de dados
- Integrações externas
- Configurações do framework

👉 O domínio não depende de frameworks ou detalhes externos

---

# 🛠️ Stack de Tecnologias

- **Java 21**
- **Spring Boot**
- **Spring Data JDBC**
- **Spring AOP**
- **OpenFeign**
- **MySQL**
- **H2 (ambiente de desenvolvimento)**
- **AWS S3**
- **OpenAPI / Swagger**

---

# 📑 Documentação da API

### 🔗 Produção
https://screenscore-api-yrw8.onrender.com/swagger-ui/index.html

### 💻 Local
http://localhost:8080/swagger-ui/index.html

---

# 🖥️ Frontend

O frontend está em um repositório separado:

👉 https://github.com/jonathan7gb/ScreenScore-UI

---

# 💡 Conclusão

A V1 do ScreenScore não foi sobre quantidade de features, mas sobre construir uma base sólida para evolução.

👉 Sistemas escalam pela qualidade das decisões, não pela complexidade.

---

# 🔮 Próximos passos

- JWT / OAuth2
- Testes automatizados (unitários e integração)
- Evolução da arquitetura
- Observabilidade e métricas

---

# 💬 Feedback

Feedbacks são sempre bem-vindos! 🚀
