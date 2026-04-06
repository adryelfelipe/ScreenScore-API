DROP TABLE IF EXISTS Filme_Genero;
DROP TABLE IF EXISTS Avaliacoes;
DROP TABLE IF EXISTS Filmes;
DROP TABLE IF EXISTS Generos;
DROP TABLE IF EXISTS Usuarios;

CREATE TABLE Usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('ADMIN','CLIENTE')),
    PRIMARY KEY (id)
);

CREATE TABLE Generos (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE Filmes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255),
    adulto BOOLEAN NOT NULL,
    data_lancamento DATE NOT NULL,
    lingua_original VARCHAR(4),
    titulo_original VARCHAR(255),
    poster CLOB,
    visao_geral CLOB NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Avaliacoes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_filme BIGINT NOT NULL,
    nota DECIMAL(4,2) NOT NULL,
    comentario CLOB,
    PRIMARY KEY (id),

    CONSTRAINT fk_avaliacao_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES Usuarios(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_avaliacao_filme
    FOREIGN KEY (id_filme)
    REFERENCES Filmes(id)
    ON DELETE CASCADE
);

CREATE TABLE Filme_Genero (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_filme BIGINT NOT NULL,
    id_genero INT NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT fk_filme_genero_filme
    FOREIGN KEY (id_filme)
    REFERENCES Filmes(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_filme_genero_genero
    FOREIGN KEY (id_genero)
    REFERENCES Generos(id)
    ON DELETE CASCADE
);