CREATE TABLE student(
  nmec INT PRIMARY KEY NOT NULL,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL
);

INSERT INTO student(nome, nmec, email) VALUES('Daniel', 111111, 'daniel@ua.pt');
INSERT INTO student(nome, nmec, email) VALUES('Ana', 222222, 'ana@ua.pt');
INSERT INTO student(nome, nmec, email) VALUES('Manel', 333333, 'manel@ua.pt');