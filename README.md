# YourTools

[//]: # ([![Quality Gate Status]&#40;https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=alert_status&#41;]&#40;https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2&#41;)

[//]: # ([![Coverage]&#40;https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=coverage&#41;]&#40;https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2&#41;)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=FerreiraJpg_YourTools2&metric=coverage)](https://sonarcloud.io/summary/new_code?id=FerreiraJpg_YourTools2)

[![Integração continua de Java com Maven](https://github.com/FerreiraJpg/YourTools2/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/FerreiraJpg/YourTools2/actions/workflows/maven.yml)

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

---
REQUISITOS FUNCIONAIS

Gestão de Amigos
|RF01 - Cadastrar amigos com nome e telefone.
|RF02 - Listar todos os amigos cadastrados.
|RF03 - Atualizar dados de amigos existentes.
|RF04 - Excluir amigos do sistema.
|RF05 - Carregar dados de um amigo específico por ID.

Gestão de Ferramentas
|RF06 - Cadastrar ferramentas com nome, marca e custo de aquisição.
|RF07 - Listar todas as ferramentas cadastradas.
|RF08 - Atualizar dados de ferramentas existentes.
|RF09 - Excluir ferramentas do sistema.
|RF10 - Carregar dados de uma ferramenta específica por ID.

Validações
|RF11 - Validar que o nome do amigo tenha pelo menos 2 caracteres.
|RF12 - Validar que o telefone do amigo tenha pelo menos 8 dígitos.
|RF13 - Validar que o nome da ferramenta tenha pelo menos 2 caracteres.
|RF14 - Validar que a marca da ferramenta não seja vazia.
|RF15 - Validar que o custo de aquisição seja maior que zero.

---

REQUISITO NÃO FUNCIONAIS

Tecnologia
|RNF01 - O sistema deve ser desenvolvido em Java 21
|RNF02 - O sistema deve usar SQLite como banco de dados
|RNF03 - O sistema deve usar Maven para gerenciar dependências
|RNF04 - O sistema deve usar interface gráfica Swing

Segurança
|RNF05 - O sistema deve usar PreparedStatements em todas as queries
|RNF06 - O sistema deve validar todos os dados antes de salvar
|RNF07 - O sistema deve tratar erros sem mostrar mensagens técnicas

Organização do Código
|RNF08 - O sistema deve separar código em 3 camadas (View, Model, DAO)
|RNF09 - O sistema deve fechar conexões de banco automaticamente
|RNF10 - O sistema deve seguir padrões de nomenclatura Java

Testes
|RNF11 - O sistema deve ter cobertura de testes maior que 70%
|RNF12 - O sistema deve executar testes automaticamente no GitHub Actions
|RNF13 - O sistema deve passar na análise do SonarCloud

Usabilidade
|RNF14 - O sistema deve responder em menos de 500ms
|RNF15 - O sistema deve mostrar mensagens de confirmação ao usuário
|RNF16 - O sistema deve atualizar tabelas após salvar/excluir dados
|RNF17 - O sistema deve pedir confirmação antes de excluir

Portabilidade
|RNF18 - O sistema deve funcionar em Windows, Linux e macOS
|RNF19 - O sistema deve criar tabelas automaticamente se não existirem
|RNF20 - O sistema deve gerar um JAR executável







