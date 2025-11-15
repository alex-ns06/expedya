/* Lógico_expedy_1: */

CREATE TABLE Clientes (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR NOT NULL,
    Email VARCHAR NOT NULL,
    Passaporte VARCHAR NOT NULL,
    Telefone VARCHAR NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL,
    Senha VARCHAR NOT NULL,
    Funcao VARCHAR NOT NULL,
    fk_Passagens_ID INT
);

CREATE TABLE Passagens (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Origem VARCHAR NOT NULL,
    Destino VARCHAR NOT NULL,
    DataPartida DATE NOT NULL,
    HoraPartida TIME NOT NULL,
    Assento VARCHAR(4) NOT NULL,
    Classe VARCHAR NOT NULL,
    fk_CompanhiaAerea_ID INT NOT NULL,
    fk_Aviao_ID INT NOT NULL
);

CREATE TABLE CompanhiaAerea (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR NOT NULL,
    CNPJ VARCHAR(18) UNIQUE NOT NULL
);

CREATE TABLE Aviao (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Modelo VARCHAR NOT NULL,
    Codigo VARCHAR NOT NULL,
    Capacidade INT NOT NULL
);

CREATE TABLE Passageiro (
    fk_Clientes_ID INT PRIMARY KEY,
    fk_CompanhiaAerea_ID INT NOT NULL
);

CREATE TABLE Pertence (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    fk_Aviao_ID INT NOT NULL,
    fk_CompanhiaAerea_ID INT NOT NULL
);

ALTER TABLE Clientes ADD CONSTRAINT FK_Clientes_2
    FOREIGN KEY (fk_Passagens_ID)
    REFERENCES Passagens (ID)
    ON DELETE CASCADE;

ALTER TABLE Passagens ADD CONSTRAINT FK_Passagens_2
    FOREIGN KEY (fk_CompanhiaAerea_ID)
    REFERENCES CompanhiaAerea (ID)
    ON DELETE RESTRICT;

ALTER TABLE Passagens ADD CONSTRAINT FK_Passagens_3
    FOREIGN KEY (fk_Aviao_ID)
    REFERENCES Aviao (ID)
    ON DELETE RESTRICT;

ALTER TABLE Passageiro ADD CONSTRAINT FK_Passageiro_1
    FOREIGN KEY (fk_CompanhiaAerea_ID)
    REFERENCES CompanhiaAerea (ID)
    ON DELETE RESTRICT;

ALTER TABLE Passageiro ADD CONSTRAINT FK_Passageiro_2
    FOREIGN KEY (fk_Clientes_ID)
    REFERENCES Clientes (ID)
    ON DELETE RESTRICT;

ALTER TABLE Pertence ADD CONSTRAINT FK_Pertence_1
    FOREIGN KEY (fk_Aviao_ID)
    REFERENCES Aviao (ID)
    ON DELETE RESTRICT;

ALTER TABLE Pertence ADD CONSTRAINT FK_Pertence_2
    FOREIGN KEY (fk_CompanhiaAerea_ID)
    REFERENCES CompanhiaAerea (ID)
    ON DELETE RESTRICT;