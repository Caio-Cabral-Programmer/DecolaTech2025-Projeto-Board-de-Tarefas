![Captura de tela 2025-04-03 140345](https://github.com/user-attachments/assets/194cf3d2-4403-4cbf-9065-751e5b3524f7)

![Captura de tela 2025-04-03 140512](https://github.com/user-attachments/assets/1bd7f080-a306-44f6-9bb2-8ccfd8a9dd25)

# Resumo do Projeto Board de Tarefas

Este projeto implementa um sistema de gerenciamento de boards (quadros) de tarefas no estilo Kanban, desenvolvido em Java. O programa permite criar, selecionar e excluir boards, além de gerenciar cards (tarefas) dentro desses boards.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal
- **JDBC**: Para conexão com banco de dados MySQL
- **Lombok**: Para redução de código boilerplate através de anotações
- **Liquibase**: Para gerenciamento e migração de esquemas de banco de dados
- **Gradle**: Como sistema de build e gerenciamento de dependências

## Funcionalidades Principais

O programa oferece uma interface de linha de comando que permite:

1. **Criar boards**: Definindo um nome e configurando colunas personalizadas
   - Cada board possui colunas padrão (INITIAL, FINAL, CANCEL) e pode ter colunas adicionais (PENDING)

2. **Selecionar boards existentes**: Visualizando e interagindo com boards já criados
   - Ao selecionar um board, o usuário pode gerenciar cards dentro dele

3. **Excluir boards**: Removendo boards que não são mais necessários

4. **Gerenciar cards**: Dentro de um board selecionado, é possível:
   - Criar novos cards
   - Mover cards entre colunas
   - Bloquear/desbloquear cards
   - Cancelar cards
   - Visualizar o board, colunas específicas ou cards individuais

A arquitetura segue um padrão de camadas com separação entre interface do usuário (UI), serviços e persistência, utilizando um banco de dados MySQL para armazenar as informações.
