/* Lógico_expedy_1: */

CREATE TABLE Clientes (
    ID SERIAL PRIMARY KEY,
    Nome VARCHAR NOT NULL,
    Email VARCHAR NOT NULL,
    Telefone VARCHAR NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL,
    Senha VARCHAR(36) NOT NULL,
    Funcao VARCHAR NOT NULL,
    fk_Passagens_ID SERIAL
);

CREATE TABLE Passagens (
    ID SERIAL PRIMARY KEY,
    Origem VARCHAR NOT NULL,
    Destino VARCHAR NOT NULL,
    DataPartida DATE NOT NULL,
    HoraPartida TIME NOT NULL,
    Assento VARCHAR(4) NOT NULL,
    Classe VARCHAR NOT NULL,
    fk_CompanhiaAerea_ID SERIAL NOT NULL,
    fk_Aviao_ID SERIAL NOT NULL
);

CREATE TABLE CompanhiaAerea (
    ID SERIAL PRIMARY KEY,
    Nome VARCHAR NOT NULL,
    CNPJ VARCHAR(18) UNIQUE NOT NULL
);

CREATE TABLE Aviao (
    ID SERIAL PRIMARY KEY,
    Modelo VARCHAR NOT NULL,
    Codigo VARCHAR NOT NULL,
    Capacidade INT NOT NULL
);

CREATE TABLE Passageiro (
    fk_Clientes_ID SERIAL PRIMARY KEY,
    fk_CompanhiaAerea_ID SERIAL NOT NULL
);

CREATE TABLE Pertence (
    ID SERIAL PRIMARY KEY,
    fk_Aviao_ID SERIAL NOT NULL,
    fk_CompanhiaAerea_ID SERIAL NOT NULL
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

CREATE USER admin_expedya WITH PASSWORD 'back_end_expedya'; -- Necessário para conectar apenas ao banco expedya

GRANT ALL PRIVILEGES ON DATABASE expedya TO admin_expedya;