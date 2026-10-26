-- SQL practice in DBeaver, for your personal MariaDB database.
-- IMPORTANT: Use another table name than books (the JPA-managed table).
-- If movie_sql already exists, choose a new table name rather than DROP it.
CREATE TABLE movie_sql (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  director VARCHAR(100),
  genre VARCHAR(50),
  rating DOUBLE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO movie_sql(title, director, genre, rating)
VALUES ('Inception', 'Christopher Nolan', 'SF', 9.1),
       ('Interstellar', 'Christopher Nolan', 'SF', 9.3),
       ('Parasite', 'Bong Joon-ho', 'Drama', 9.0);

SELECT * FROM movie_sql;
SELECT title, director, rating FROM movie_sql;
SELECT * FROM movie_sql WHERE genre = 'SF';

UPDATE movie_sql SET rating = 9.5 WHERE title = 'Interstellar';
SELECT * FROM movie_sql WHERE title = 'Interstellar';

DELETE FROM movie_sql WHERE title = 'Parasite';
SELECT * FROM movie_sql;
