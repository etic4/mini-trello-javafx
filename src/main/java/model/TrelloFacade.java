package model;

public class TrelloFacade {
    private final DaoFactory dao;

    public TrelloFacade() {
        this.dao = new DaoFactory(DaoBackendType.SQLITE);
    }

    public BoardFacade getBoardFacade() {
        var board = loadBoard(1);
        return new BoardFacade(board);
    }

    public void seedData() {
        dao.seedData();
    }

    private Board loadBoard(int boardId) {
        var board = dao.getBoardDao().get(boardId);

        var columns = dao.getColumnDao().get_all(boardId);
        board.addAll(columns);

        for (var column : columns) {
            var cards = dao.getCardDao().get_all(column.getId());
            column.addAll(cards);
        }

        return board;
    }
}
