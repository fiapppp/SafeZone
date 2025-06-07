-- PERFIL
CREATE TABLE Perfil (
                        id NUMBER PRIMARY KEY,
                        nome_perfil VARCHAR2(255) NOT NULL,
                        status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1))
);
CREATE SEQUENCE Perfil_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Perfil_BI
    BEFORE INSERT ON Perfil FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Perfil_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- USUARIO
CREATE TABLE Usuario (
                         id NUMBER PRIMARY KEY,
                         email VARCHAR2(255) UNIQUE NOT NULL,
                         senha VARCHAR2(255) NOT NULL,
                         cpf VARCHAR2(20) UNIQUE,
                         telefone VARCHAR2(20),
                         nome VARCHAR2(255) NOT NULL,
                         tipo_chave_pix NUMBER,
                         status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1)),
                         chave_pix VARCHAR2(255) UNIQUE,
                         pontuacao_total NUMBER DEFAULT 0 CHECK (pontuacao_total >= 0),
                         id_perfil NUMBER,
                         data_nascimento DATE,
                         CONSTRAINT FK_Usuario_Perfil FOREIGN KEY (id_perfil) REFERENCES Perfil(id) ON DELETE SET NULL
);
CREATE SEQUENCE Usuario_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Usuario_BI
    BEFORE INSERT ON Usuario FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Usuario_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- LOCALIZACAO
CREATE TABLE Localizacao (
                             id NUMBER PRIMARY KEY,
                             nome_estacao VARCHAR2(255) NOT NULL,
                             linha VARCHAR2(255),
                             plataforma VARCHAR2(255),
                             latitude_longitude VARCHAR2(255),
                             status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1))
);
CREATE SEQUENCE Localizacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Localizacao_BI
    BEFORE INSERT ON Localizacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Localizacao_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- CATEGORIA
CREATE TABLE Categoria (
                           id NUMBER PRIMARY KEY,
                           descricao VARCHAR2(255) NOT NULL,
                           status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1))
);
CREATE SEQUENCE Categoria_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Categoria_BI
    BEFORE INSERT ON Categoria FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Categoria_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- GRUPODENUNCIA
CREATE TABLE GrupoDenuncia (
                               id NUMBER PRIMARY KEY,
                               descricao_grupo VARCHAR2(255) NOT NULL,
                               status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1)),
                               data_criacao DATE NOT NULL,
                               id_responsavel NUMBER,
                               observacao_responsavel VARCHAR2(4000),
                               prioridade NUMBER DEFAULT 0 CHECK (prioridade BETWEEN 0 AND 5)
);
CREATE SEQUENCE GrupoDenuncia_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER GrupoDenuncia_BI
    BEFORE INSERT ON GrupoDenuncia FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT GrupoDenuncia_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- DENUNCIA
CREATE TABLE Denuncia (
                          id NUMBER PRIMARY KEY,
                          data_denuncia DATE NOT NULL,
                          descricao VARCHAR2(4000) NOT NULL,
                          status NUMBER(1) DEFAULT 0 CHECK (status IN (0, 1, 2, 3)),
                          data_conclusao DATE,
                          observacao_responsavel VARCHAR2(4000),
                          prioridade NUMBER DEFAULT 0 CHECK (prioridade BETWEEN 0 AND 5),
                          id_usuario NUMBER,
                          id_categoria NUMBER,
                          id_grupoDenuncia NUMBER,
                          id_localizacao NUMBER,
                          informacao_adicional VARCHAR2(4000),
                          CONSTRAINT FK_Denuncia_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_Categoria FOREIGN KEY (id_categoria) REFERENCES Categoria(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_GrupoDenuncia FOREIGN KEY (id_grupoDenuncia) REFERENCES GrupoDenuncia(id) ON DELETE SET NULL,
                          CONSTRAINT FK_Denuncia_Localizacao FOREIGN KEY (id_localizacao) REFERENCES Localizacao(id) ON DELETE SET NULL
);
CREATE SEQUENCE Denuncia_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Denuncia_BI
    BEFORE INSERT ON Denuncia FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Denuncia_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- PONTUACAO
CREATE TABLE Pontuacao (
                           id NUMBER PRIMARY KEY,
                           valor_pontos NUMBER NOT NULL,
                           data_concessao DATE NOT NULL,
                           id_denuncia NUMBER NOT NULL,
                           status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1)),
                           data_atualizacao DATE,
                           CONSTRAINT FK_Pontuacao_Denuncia FOREIGN KEY (id_denuncia) REFERENCES Denuncia(id)
);
CREATE SEQUENCE Pontuacao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Pontuacao_BI
    BEFORE INSERT ON Pontuacao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Pontuacao_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- HISTORICODENUNCIA
CREATE TABLE HistoricoDenuncia (
                                   id NUMBER PRIMARY KEY,
                                   data_alteracao DATE NOT NULL,
                                   status_anterior NUMBER(1),
                                   status_atual NUMBER(1),
                                   id_usuario NUMBER,
                                   acao VARCHAR2(4000),
                                   id_denuncia NUMBER,
                                   CONSTRAINT FK_HistoricoDenuncia_Denuncia FOREIGN KEY (id_denuncia) REFERENCES Denuncia(id) ON DELETE SET NULL
);
CREATE SEQUENCE HistoricoDenuncia_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER HistoricoDenuncia_BI
    BEFORE INSERT ON HistoricoDenuncia FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT HistoricoDenuncia_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- EVIDENCIA
CREATE TABLE Evidencia (
                           tipo_evidencia VARCHAR2(255) NOT NULL,
                           arquivo VARCHAR2(255) NOT NULL,
                           id NUMBER PRIMARY KEY,
                           id_denuncia NUMBER,
                           CONSTRAINT FK_Evidencia_Denuncia FOREIGN KEY (id_denuncia) REFERENCES Denuncia(id) ON DELETE SET NULL
);
CREATE SEQUENCE Evidencia_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Evidencia_BI
    BEFORE INSERT ON Evidencia FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Evidencia_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- TIPORECOMPENSA
CREATE TABLE TipoRecompensa (
                                id NUMBER PRIMARY KEY,
                                descricao VARCHAR2(255) NOT NULL,
                                status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1))
);
CREATE SEQUENCE TipoRecompensa_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER TipoRecompensa_BI
    BEFORE INSERT ON TipoRecompensa FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT TipoRecompensa_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- PARCEIRO
CREATE TABLE Parceiro (
                          id NUMBER PRIMARY KEY,
                          nome_parceiro VARCHAR2(255) NOT NULL,
                          contato VARCHAR2(255) NOT NULL,
                          cpnj VARCHAR2(20) UNIQUE,
                          status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1))
);
CREATE SEQUENCE Parceiro_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Parceiro_BI
    BEFORE INSERT ON Parceiro FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Parceiro_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- RECOMPENSA
CREATE TABLE Recompensa (
                            id NUMBER PRIMARY KEY,
                            custo_pontos NUMBER NOT NULL,
                            data_validade DATE,
                            descricao VARCHAR2(4000) NOT NULL,
                            quantidade_disponivel NUMBER DEFAULT 0 CHECK (quantidade_disponivel >= 0),
                            valor DECIMAL(10, 2) NOT NULL,
                            status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1)),
                            id_tipo_recompensa NUMBER,
                            id_parceiro NUMBER,
                            CONSTRAINT FK_Recompensa_TipoRecompensa FOREIGN KEY (id_tipo_recompensa) REFERENCES TipoRecompensa(id) ON DELETE SET NULL,
                            CONSTRAINT FK_Recompensa_Parceiro FOREIGN KEY (id_parceiro) REFERENCES Parceiro(id) ON DELETE SET NULL
);
CREATE SEQUENCE Recompensa_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Recompensa_BI
    BEFORE INSERT ON Recompensa FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Recompensa_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- RESGATERECOMPENSA
CREATE TABLE ResgateRecompensa (
                                   id NUMBER PRIMARY KEY,
                                   data_resgate DATE NOT NULL,
                                   status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1)),
                                   id_usuario NUMBER NOT NULL,
                                   id_recompensa NUMBER NOT NULL,
                                   CONSTRAINT FK_ResgateRecompensa_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id),
                                   CONSTRAINT FK_ResgateRecompensa_Recompensa FOREIGN KEY (id_recompensa) REFERENCES Recompensa(id)
);
CREATE SEQUENCE ResgateRecompensa_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER ResgateRecompensa_BI
    BEFORE INSERT ON ResgateRecompensa FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT ResgateRecompensa_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;


-- PERMISSAO
CREATE TABLE Permissao (
                           id NUMBER PRIMARY KEY,
                           identificador VARCHAR2(255) NOT NULL,
                           codigo VARCHAR2(255) NOT NULL,
                           status NUMBER(1) DEFAULT 1 CHECK (status IN (0, 1))
);
CREATE SEQUENCE Permissao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE OR REPLACE TRIGGER Permissao_BI
    BEFORE INSERT ON Permissao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN SELECT Permissao_SEQ.NEXTVAL INTO :NEW.id FROM dual; END;



-- Sequence para gerar IDs
CREATE SEQUENCE Sessao_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- Tabela de Sessão
CREATE TABLE Sessao (
                        id               NUMBER PRIMARY KEY,
                        token            VARCHAR2(255) UNIQUE NOT NULL,
                        id_usuario       NUMBER NOT NULL,
                        data_expiracao   TIMESTAMP NOT NULL,
                        CONSTRAINT FK_Sessao_Usuario FOREIGN KEY (id_usuario)
                            REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Trigger para preencher o ID automaticamente
CREATE OR REPLACE TRIGGER Sessao_BI
    BEFORE INSERT ON Sessao FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT Sessao_SEQ.NEXTVAL
    INTO :NEW.id
    FROM dual;
END;
