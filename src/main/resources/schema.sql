CREATE TABLE IF NOT EXISTS book (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       publication_year INT
);

CREATE TABLE IF NOT EXISTS customer
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

# INSERT INTO book (title, author, publication_year) VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 1925);
# INSERT INTO book (title, author, publication_year) VALUES ('1984', 'George Orwell', 1949);
# INSERT INTO book (title, author, publication_year) VALUES ('Pride and Prejudice', 'Jane Austen', 1813);
# INSERT INTO book (title, author, publication_year) VALUES ('The Catcher in the Rye', 'J.D. Salinger', 1951);
# INSERT INTO book (title, author, publication_year) VALUES ('The Hobbit', 'J.R.R. Tolkien', 1937);
# INSERT INTO book (title, author, publication_year) VALUES ('Moby Dick', 'Herman Melville', 1851);
# INSERT INTO book (title, author, publication_year) VALUES ('War and Peace', 'Leo Tolstoy', 1869);
# INSERT INTO book (title, author, publication_year) VALUES ('Ulysses', 'James Joyce', 1922);
