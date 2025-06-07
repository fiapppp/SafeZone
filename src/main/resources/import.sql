-- Status:
-- 1 - ativo
-- 2 - desativado

-- PERFIL
CREATE TABLE Perfil (
                        id NUMBER PRIMARY KEY,
                        nome_perfil VARCHAR2(255) NOT NULL,
                        status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2))
);

CREATE SEQUENCE Perfil_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
-- Trigger para preencher o ID automaticamente
CREATE OR REPLACE TRIGGER Perfil_BI
    BEFORE INSERT ON Perfil FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Perfil_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- LOCALIZACAO
CREATE TABLE Localizacao (
                             id NUMBER PRIMARY KEY,
                             status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                             logradouro VARCHAR2(255),
                             bairro VARCHAR2(255),
                             cidade VARCHAR2(255),
                             estado VARCHAR2(255),
                             CEP VARCHAR2(255),
                             numero NUMBER,
                             latitude_longitude VARCHAR2(255)
);

CREATE SEQUENCE Localizacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Localizacao_BI
    BEFORE INSERT ON Localizacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Localizacao_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- USUARIO
CREATE TABLE Usuario (
                         id NUMBER PRIMARY KEY,
                         status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                         nome VARCHAR2(255) NOT NULL,
                         data_nascimento DATE,
                         email VARCHAR2(255) UNIQUE NOT NULL,
                         senha VARCHAR2(255) NOT NULL,
                         cpf VARCHAR2(20) UNIQUE,
                         telefone VARCHAR2(20),
                         id_perfil NUMBER,
                         id_localizacao NUMBER,
                         CONSTRAINT FK_Usuario_Perfil FOREIGN KEY (id_perfil) REFERENCES Perfil(id) ON DELETE SET NULL,
                         CONSTRAINT FK_Usuario_Localizacao FOREIGN KEY (id_localizacao) REFERENCES Localizacao(id) ON DELETE SET NULL
);

CREATE SEQUENCE Usuario_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Usuario_BI
    BEFORE INSERT ON Usuario FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Usuario_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- CATEGORIA
CREATE TABLE Categoria (
                           id NUMBER PRIMARY KEY,
                           status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                           nome VARCHAR2(255) NOT NULL,
                           descricao VARCHAR2(255)
);

CREATE SEQUENCE Categoria_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Categoria_BI
    BEFORE INSERT ON Categoria FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Categoria_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- Ocorrencia
CREATE TABLE Ocorrencia (
                            id NUMBER PRIMARY KEY,
                            status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                            prioridade NUMBER DEFAULT 0 CHECK (prioridade BETWEEN 0 AND 3),
                            data_criacao DATE NOT NULL,
                            titulo VARCHAR2(255) NOT NULL,
                            descricao VARCHAR2(4000),
                            raio_auxilio VARCHAR2(255),
                            id_localizacao NUMBER,
                            CONSTRAINT FK_Ocorrencia_Localizacao FOREIGN KEY (id_localizacao) REFERENCES Localizacao(id) ON DELETE SET NULL,
                            id_categoria NUMBER,
                            CONSTRAINT FK_Ocorrencia_Categoria FOREIGN KEY (id_categoria) REFERENCES Categoria(id) ON DELETE SET NULL
);

CREATE SEQUENCE Ocorrencia_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Ocorrencia_BI
    BEFORE INSERT ON Ocorrencia FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Ocorrencia_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- DENUNCIA
-- Status:
-- 1 - em analise
-- 2 - reprovado
-- 3 - aprovado
CREATE TABLE Denuncia (
                          id NUMBER PRIMARY KEY,
                          data_denuncia DATE NOT NULL,
                          data_conclusao DATE,
                          status NUMBER(1) DEFAULT 0 CHECK (status IN (1, 2, 3)),
                          prioridade NUMBER DEFAULT 0 CHECK (prioridade BETWEEN 0 AND 3),
                          titulo VARCHAR2(255) NOT NULL,
                          descricao VARCHAR2(4000),
                          observacao_responsavel VARCHAR2(4000),
                          id_cidadao_prejudicado NUMBER,
                          id_cidadao_apoidador NUMBER,
                          id_categoria NUMBER,
                          id_ocorrencia NUMBER,
                          id_localizacao NUMBER,
                          CONSTRAINT FK_Denuncia_Usuario_Prejudicado FOREIGN KEY (id_cidadao_prejudicado) REFERENCES Usuario(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_Usuario_Apoidador FOREIGN KEY (id_cidadao_apoidador) REFERENCES Usuario(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_Categoria FOREIGN KEY (id_categoria) REFERENCES Categoria(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_Ocorrencia FOREIGN KEY (id_ocorrencia) REFERENCES Ocorrencia(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_Localizacao FOREIGN KEY (id_localizacao) REFERENCES Localizacao(id) ON DELETE SET NULL
);

CREATE SEQUENCE Denuncia_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Denuncia_BI
    BEFORE INSERT ON Denuncia FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Denuncia_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- PONTUACAO
CREATE TABLE Pontuacao (
                           id NUMBER PRIMARY KEY,
                           status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                           data_concessao DATE NOT NULL,
                           valor_pontos NUMBER NOT NULL,
                           id_denuncia NUMBER NOT NULL,
                           CONSTRAINT FK_Pontuacao_Denuncia FOREIGN KEY (id_denuncia) REFERENCES Denuncia(id)
);

CREATE SEQUENCE Pontuacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Pontuacao_BI
    BEFORE INSERT ON Pontuacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Pontuacao_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- TIPODOACAO
CREATE TABLE TipoDoacao (
                            id NUMBER PRIMARY KEY,
                            status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                            titulo VARCHAR2(255) NOT NULL,
                            descricao VARCHAR2(255)
);

CREATE SEQUENCE TipoDoacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER TipoDoacao_BI
    BEFORE INSERT ON TipoDoacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT TipoDoacao_SEQ.NEXTVAL
      INTO :NEW.id
      FROM dual;
END;

-- =============================================================================
-- DOACAO
CREATE TABLE Doacao (
                        id NUMBER PRIMARY KEY,
                        status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                        titulo VARCHAR2(4000) NOT NULL,
                        descricao VARCHAR2(4000),
                        custo_pontos NUMBER NOT NULL,
                        data_validade DATE,
                        habilitado_conversao  NUMBER(1) DEFAULT 0 CHECK (habilitado_conversao IN (0, 1)),
                        quantidade_disponivel NUMBER DEFAULT 0 CHECK (quantidade_disponivel >= 0),
                        id_tipo_doacao NUMBER,
                        CONSTRAINT FK_Doacao_TipoDoacao FOREIGN KEY (id_tipo_doacao) REFERENCES TipoDoacao(id) ON DELETE SET NULL
);

CREATE SEQUENCE Doacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Doacao_BI
    BEFORE INSERT ON Doacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Doacao_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;

-- =============================================================================
-- RESGATEDOACAO
CREATE TABLE ResgateDoacao (
                               id NUMBER PRIMARY KEY,
                               data_resgate DATE NOT NULL,
                               status NUMBER(1) DEFAULT 1 CHECK (status IN (1, 2)),
                               id_usuario NUMBER NOT NULL,
                               id_doacao NUMBER NOT NULL,
                               CONSTRAINT FK_ResgateDoacao_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id),
                               CONSTRAINT FK_ResgateDoacao_Doacao FOREIGN KEY (id_doacao) REFERENCES Doacao(id)
);

CREATE SEQUENCE ResgateDoacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER ResgateDoacao_BI
    BEFORE INSERT ON ResgateDoacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT ResgateDoacao_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;


-- =============================================================================
-- Tabela de Sessão
CREATE TABLE Sessao (
                        id               NUMBER PRIMARY KEY,
                        token            VARCHAR2(255) UNIQUE NOT NULL,
                        data_expiracao   TIMESTAMP NOT NULL,
                        id_usuario       NUMBER NOT NULL,
                        CONSTRAINT FK_Sessao_Usuario FOREIGN KEY (id_usuario)
                            REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE SEQUENCE Sessao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE OR REPLACE TRIGGER Sessao_BI
    BEFORE INSERT ON Sessao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Sessao_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
