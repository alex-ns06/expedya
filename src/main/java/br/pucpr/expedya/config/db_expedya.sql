----------------------------------------------------------
-- DROPS (na ordem correta por causa das FKs)
----------------------------------------------------------
DROP TABLE IF EXISTS Pertence;
DROP TABLE IF EXISTS Passageiro;
DROP TABLE IF EXISTS Passagens;
DROP TABLE IF EXISTS Clientes;
DROP TABLE IF EXISTS Aviao;
DROP TABLE IF EXISTS CompanhiaAerea;

----------------------------------------------------------
-- CREATE TABLES
----------------------------------------------------------

CREATE TABLE CompanhiaAerea (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    CNPJ VARCHAR(18) UNIQUE NOT NULL
);

CREATE TABLE Aviao (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Modelo VARCHAR(255) NOT NULL,
    Codigo VARCHAR(255) NOT NULL,
    Capacidade INT NOT NULL
);

CREATE TABLE Passagens (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Origem VARCHAR(255) NOT NULL,
    Destino VARCHAR(255) NOT NULL,
    DataPartida DATE NOT NULL,
    HoraPartida TIME NOT NULL,
    Assento VARCHAR(4) NOT NULL,
    Classe VARCHAR(255) NOT NULL,
    fk_CompanhiaAerea_ID INT NOT NULL,
    fk_Aviao_ID INT NOT NULL,
    CONSTRAINT FK_Passagens_Companhia FOREIGN KEY (fk_CompanhiaAerea_ID)
        REFERENCES CompanhiaAerea(ID) ON DELETE RESTRICT,
    CONSTRAINT FK_Passagens_Aviao FOREIGN KEY (fk_Aviao_ID)
        REFERENCES Aviao(ID) ON DELETE RESTRICT
);

CREATE TABLE Clientes (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Passaporte VARCHAR(255) NOT NULL,
    Telefone VARCHAR(255) NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL,
    Senha VARCHAR(255) NOT NULL,
    Funcao VARCHAR(255) NOT NULL,
    fk_Passagens_ID INT,
    CONSTRAINT FK_Clientes_Passagens FOREIGN KEY (fk_Passagens_ID)
        REFERENCES Passagens(ID) ON DELETE CASCADE
);

CREATE TABLE Passageiro (
    fk_Clientes_ID INT PRIMARY KEY,
    fk_CompanhiaAerea_ID INT NOT NULL,
    CONSTRAINT FK_Passageiro_Cliente FOREIGN KEY (fk_Clientes_ID)
        REFERENCES Clientes(ID) ON DELETE RESTRICT,
    CONSTRAINT FK_Passageiro_Companhia FOREIGN KEY (fk_CompanhiaAerea_ID)
        REFERENCES CompanhiaAerea(ID) ON DELETE RESTRICT
);

CREATE TABLE Pertence (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    fk_Aviao_ID INT NOT NULL,
    fk_CompanhiaAerea_ID INT NOT NULL,
    CONSTRAINT FK_Pertence_Aviao FOREIGN KEY (fk_Aviao_ID)
        REFERENCES Aviao(ID) ON DELETE RESTRICT,
    CONSTRAINT FK_Pertence_Companhia FOREIGN KEY (fk_CompanhiaAerea_ID)
        REFERENCES CompanhiaAerea(ID) ON DELETE RESTRICT
);
