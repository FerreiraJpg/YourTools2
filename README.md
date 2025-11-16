# YourTools
[![Qodana](https://github.com/FerreiraJpg/YourTools2/actions/workflows/code_quality.yml/badge.svg)](https://github.com/FerreiraJpg/YourTools2/actions/workflows/code_quality.yml)'

[//]: # ([![Quality Gate Status]&#40;https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=alert_status&#41;]&#40;https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2&#41;)

[//]: # ([![Coverage]&#40;https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=coverage&#41;]&#40;https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2&#41;)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=coverage)](https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2)

Trabalho do primeiro semestre da faculdade.
### 🧩 Grupo do Projeto Original

| Integrante | RA | GitHub |
|-------------|------|--------|
| Edric Leoni Savio | 1072310470 | [@edricls](https://github.com/edricls) |
| Pedro Schreiner Ferreira | 1072310948 | [@FerreiraJpg](https://github.com/FerreiraJpg) |
| Iago Alexandre Marcaccini Panho | 1072312987 | [@Iag18](https://github.com/Iag18) |
| Kauan Correia | 1072314544 | — |
| João Bernardo Varela de Amorim | 10723114376 | — |

---

### 🧠 Grupo de Gestão e Qualidade de Software

| Integrante | RA | GitHub |
|-------------|------|--------|
| Edric Leoni Savio | 1072310470 | [@edricls](https://github.com/edricls) |
| Pedro Schreiner Ferreira | 1072310948 | [@FerreiraJpg](https://github.com/FerreiraJpg) |
| Iago Alexandre Marcaccini Panho | 1072312987 | [@Iag18](https://github.com/Iag18) |
| Pedro Inacio Pinheiro Alcantara Dias | 10725215730 | [@opedroalcantara](https://github.com/opedroalcantara) |
| Clara Emidio Corrêa | 10722129245 | [@ClaraCorrea](https://github.com/ClaraCorrea) |


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




