CREATE TABLE tb_java_iafuture_contas
(
    id_conta    BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(255),
    telefone    VARCHAR(20),
    cnpj        VARCHAR(20),
    cep         VARCHAR(10),
    logradouro  VARCHAR(255),
    complemento VARCHAR(255),
    bairro      VARCHAR(255),
    cidade      VARCHAR(255),
    estado      VARCHAR(2),
    numero      VARCHAR(10),
    username    VARCHAR(255),
    senha       VARCHAR(255)
);