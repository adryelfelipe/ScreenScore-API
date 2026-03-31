INSERT INTO Generos (id, nome) VALUES
    (1,'Aventura'),
    (2,'Fantasia'),
    (3,'Animação'),
    (4,'Drama'),
    (5,'Terror'),
    (6,'Ação'),
    (7,'Comédia'),
    (8,'História'),
    (9,'Faroeste'),
    (10,'Thriller'),
    (11,'Crime'),
    (12,'Documentário'),
    (13,'Ficção científica'),
    (14,'Mistério'),
    (15,'Música'),
    (16,'Romance'),
    (17,'Família'),
    (18,'Guerra'),
    (19,'Cinema TV');

INSERT INTO Filmes (titulo, adulto, data_lancamento, lingua_original, titulo_original, poster, visao_geral) VALUES
    ('Inception', FALSE, DATE '2010-07-16', 'en', 'Inception', NULL, 'Um ladrão que invade sonhos recebe a missão de plantar uma ideia.'),
    ('The Matrix', FALSE, DATE '1999-03-31', 'en', 'The Matrix', NULL, 'Um hacker descobre a verdadeira natureza da realidade.'),
    ('Interstellar', FALSE, DATE '2014-11-07', 'en', 'Interstellar', NULL, 'Exploradores viajam pelo espaço em busca de um novo lar para a humanidade.'),
    ('Parasite', FALSE, DATE '2019-05-30', 'ko', 'Gisaengchung', NULL, 'Uma família pobre se infiltra na vida de uma família rica.'),
    ('The Godfather', FALSE, DATE '1972-03-24', 'en', 'The Godfather', NULL, 'A história da poderosa família mafiosa Corleone.'),
    ('Pulp Fiction', FALSE, DATE '1994-10-14', 'en', 'Pulp Fiction', NULL, 'Histórias interligadas do submundo do crime em Los Angeles.'),
    ('The Dark Knight', FALSE, DATE '2008-07-18', 'en', 'The Dark Knight', NULL, 'Batman enfrenta o caos causado pelo Coringa.'),
    ('Fight Club', TRUE, DATE '1999-10-15', 'en', 'Fight Club', NULL, 'Um homem cria um clube secreto de luta.'),
    ('Forrest Gump', FALSE, DATE '1994-07-06', 'en', 'Forrest Gump', NULL, 'A vida de um homem simples que testemunha grandes eventos históricos.'),
    ('Spirited Away', FALSE, DATE '2001-07-20', 'ja', 'Sen to Chihiro no Kamikakushi', NULL, 'Uma garota entra em um mundo mágico e precisa salvar seus pais.'),
    ('Gladiator', FALSE, DATE '2000-05-05', 'en', 'Gladiator', NULL, 'Um general romano busca vingança contra um imperador corrupto.'),
    ('Titanic', FALSE, DATE '1997-12-19', 'en', 'Titanic', NULL, 'Uma história de amor a bordo do famoso navio.'),
    ('The Shawshank Redemption', FALSE, DATE '1994-09-23', 'en', 'The Shawshank Redemption', NULL, 'Um homem preso injustamente encontra esperança na amizade.'),
    ('Avengers: Endgame', FALSE, DATE '2019-04-26', 'en', 'Avengers: Endgame', NULL, 'Os Vingadores enfrentam Thanos em uma batalha final.'),
    ('Joker', TRUE, DATE '2019-10-04', 'en', 'Joker', NULL, 'A origem de um dos maiores vilões de Gotham.');

INSERT INTO Usuarios (nome, email, senha, tipo_usuario) VALUES
    ('Admin Principal', 'adryelgamer@gmail.com', '123456Aa@', 'ADMIN'),
    ('João Silva', 'joao@email.com', 'Joao@1234', 'CLIENTE'),
    ('Maria Souza', 'maria@email.com', 'Maria@2024', 'CLIENTE'),
    ('Carlos Lima', 'carlos@email.com', 'Carlos@99', 'CLIENTE'),
    ('Ana Pereira', 'ana@email.com', 'Ana@12345', 'CLIENTE');

INSERT INTO Avaliacoes (id_usuario, id_filme, nota, comentario) VALUES
(1, 1, 9, 'Filme incrível, recomendo para todos!'),
(2, 2, 8, 'Muito bom, roteiro interessante.'),
(3, 3, 7, 'Gostei, mas poderia ter sido melhor.'),
(4, 4, 10, 'Perfeito! Um dos melhores que já vi.'),
(5, 5, 6, 'Assistível, mas esperava mais da história.');

INSERT INTO Avaliacoes (id_usuario, id_filme, nota, comentario)
SELECT
    CAST(FLOOR(RAND() * 5) + 1 AS INT) AS id_usuario,
    f.id AS id_filme,
    CAST(FLOOR(RAND() * 11) AS INT) AS nota, -- 0 a 10
    'Avaliação automática'
FROM Filmes f
JOIN (
    SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL
    SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL
    SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL
    SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL
    SELECT 13 UNION ALL SELECT 14
) nums
ON 1=1
WHERE f.id <= 15;

INSERT INTO Filme_Genero (id_filme, id_genero) VALUES
(1, 1), -- Inception → Ação
(1, 2), -- Inception → Ficção
(2, 1), -- The Matrix → Ação
(2, 2), -- The Matrix → Ficção
(3, 1), -- Interstellar → Ação
(3, 2), -- Interstellar → Ficção
(4, 3), -- Parasite → Drama
(4, 6), -- Parasite → Suspense
(5, 3), -- The Godfather → Drama
(5, 4), -- The Godfather → Crime
(6, 4), -- Pulp Fiction → Crime
(6, 3), -- Pulp Fiction → Drama
(7, 1), -- The Dark Knight → Ação
(7, 6), -- The Dark Knight → Suspense
(8, 6), -- Fight Club → Suspense
(8, 3), -- Fight Club → Drama
(9, 3), -- Forrest Gump → Drama
(9, 7), -- Forrest Gump → Romance
(10, 5), -- Spirited Away → Animação
(10, 7); -- Spirited Away → Romance