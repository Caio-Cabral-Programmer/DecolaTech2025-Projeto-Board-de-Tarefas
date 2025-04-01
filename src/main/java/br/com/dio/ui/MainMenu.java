package br.com.dio.ui;

import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardColumnKindEnum;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.FINAL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.INITIAL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.PENDING;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada:");
        var option = -1;
        while (true){
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            option = scanner.nextInt();
            switch (option){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board:");
        entity.setName(scanner.next());

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial (INITIAL):");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        System.out.println("Seu board terá colunas além das 3 padrões (INITIAL, FINAL e CANCEL)? Se sim informe quantas, senão digite '0':");
        var additionalColumns = scanner.nextInt();

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da nova coluna (PENDING):");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final (FINAL):");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento (CANCEL):");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }

    }

    private void selectBoard() throws SQLException {
        try(var connection = getConnection()){
            var queryService = new BoardQueryService(connection);

            // Buscar todos os boards disponíveis
            List<BoardEntity> allBoards = queryService.findAllBoards();

            if (allBoards.isEmpty()) {
                System.out.println("Não existem boards cadastrados. Crie um novo board primeiro.");
                return;
            }

            // Exibir a lista de boards disponíveis (antes não tinha esta feature de exibição de todos os boards.)
            System.out.println("\n===== BOARDS DISPONÍVEIS =====");
            System.out.println("ID  | NOME");
            System.out.println("-----------------");

            for (BoardEntity board : allBoards) {
                System.out.printf("%-3d | %s\n", board.getId(), board.getName());
            }

            System.out.println("-----------------");
            System.out.println("Informe o id do board que deseja selecionar:");

            var id = scanner.nextLong();
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um board com id %s\n", id)
            );
        }
    }

    private void deleteBoard() throws SQLException {
        try(var connection = getConnection()){
            var queryService = new BoardQueryService(connection);

            // Buscar todos os boards disponíveis
            List<BoardEntity> allBoards = queryService.findAllBoards();

            if (allBoards.isEmpty()) {
                System.out.println("Não existem boards cadastrados para excluir.");
                return;
            }

            // Exibir a lista de boards disponíveis (antes não tinha esta feature de exibição de todos os boards.)
            System.out.println("\n===== BOARDS DISPONÍVEIS =====");
            System.out.println("ID  | NOME");
            System.out.println("-----------------");

            for (BoardEntity board : allBoards) {
                System.out.printf("%-3d | %s\n", board.getId(), board.getName());
            }

            System.out.println("-----------------");
            System.out.println("Informe o id do board que será excluído:");

            var id = scanner.nextLong();
            var service = new BoardService(connection);
            if (service.delete(id)){
                System.out.printf("O board %s foi excluido\n", id);
            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }
        }
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }

}

