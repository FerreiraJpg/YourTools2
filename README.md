# YourTools
Trabalho do primeiro semestre da faculdade.
Grupo do projeto original:
Edric Leoni Savio RA: 1072310470
Pedro Schreiner Ferreira RA: 1072310948
Iago Alexandre marcaccini panho RA: 1072312987
Kauan Correia RA: 1072314544
João Bernardo Varela de Amorim RA: 10723114376

Grupo de Gestão e Qualidade de Software:
Edric Leoni Savio RA: 1072310470
Pedro Schreiner Ferreira RA: 1072310948
Iago Alexandre marcaccini panho RA: 1072312987
Pedro Inacio Pinheiro Alcantra Dias RA: 10725215730
Clara Emidio Corrêa RA: 10722129245






Para utilizar o app, é preciso baixar o mysql workbench e criar um banco de dados local, para ai sim sincronizar e fazer o app funcionar.

Por conta de rodar em um banco de dados local é necessario a pessoa criar e sincronizar ele com o netbeans, usando o MySQL

CREATE DATABASE db_yourtools;
USE db_yourtools;

CREATE TABLE tb_amigos (
    id INT PRIMARY KEY,
    nome VARCHAR(100),
    telefone INT
);

CREATE TABLE tb_ferramentas (
    id INT PRIMARY KEY,
    nome VARCHAR(100),
    marca VARCHAR(100),
    custoAquisicao DOUBLE
);

Essa a base do banco que você precisa copiar no MySQL

