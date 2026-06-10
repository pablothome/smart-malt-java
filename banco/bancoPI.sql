-- =====================================
-- BANCO DE DADOS SMART MALT
-- Sistema de Controle de Estoque
-- =====================================

-- 1 CRIAÇÃO DO BANCO
-- CREATE DATABASE smart_malt;
-- USE smart_malt;

-- 2 TABELA USUÁRIO
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome_usuario VARCHAR(50),
    login_usuario VARCHAR(30),
    senha_usuario VARCHAR(100),
    perfil_usuario VARCHAR(20)
);

-- 3 TABELA CLIENTE
CREATE TABLE cliente (
    cpf_cliente VARCHAR(11) PRIMARY KEY,
    nome_cliente VARCHAR(50),
    telefone_cliente VARCHAR(15),
    email_cliente VARCHAR(50)
);

-- 4 TABELA FORNECEDOR
CREATE TABLE fornecedor (
    id_fornecedor INT PRIMARY KEY AUTO_INCREMENT,
    nome_fornecedor VARCHAR(50),
    marca_produto VARCHAR(40),
    telefone_fornecedor VARCHAR(15),
    cnpj_fornecedor VARCHAR(18),
    email_fornecedor VARCHAR(50)
);

-- 5 TABELA PRODUTO
CREATE TABLE produto (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    nome_produto VARCHAR(50),
    marca_produto VARCHAR(40),
    categoria_produto VARCHAR(30),
    preco_produto DECIMAL(10,2),
    quantidade_estoque INT
);

-- 6 TABELA PEDIDO
CREATE TABLE pedido (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    cpf_cliente VARCHAR(11),
    data_pedido DATE,
    FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf_cliente)
);

-- 7 TABELA ITEM_PEDIDO
CREATE TABLE item_pedido (
    id_item INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT,
    id_produto INT,
    quantidade INT,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

CREATE TABLE entrada (
	id_entrada INT PRIMARY KEY AUTO_INCREMENT,
	id_fornecedor INT,
	data_entrada DATE,
	FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id_fornecedor)
);

CREATE TABLE item_entrada (
	id_item INT PRIMARY KEY AUTO_INCREMENT,
	id_entrada INT,
	id_produto INT,
	quantidade INT,
	preco_compra DECIMAL (10, 2),
	FOREIGN KEY (id_entrada) REFERENCES entrada(id_entrada),
	FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);































