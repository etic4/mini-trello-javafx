package model;

public class TrelloFacade {
    private final DaoFactory dao;
    private BoardFacade boardFacade;

    public TrelloFacade() {
        this.dao = new DaoFactory();
    }

    public BoardFacade buildBoardFacade() {
        var board = loadBoard(1);
        boardFacade = new BoardFacade(board);
        return boardFacade;
    }

    public BoardFacade getBoardFacade() {
        return boardFacade;
    }

    public void seedData() {
        dao.seedData();
    }

    private Board loadBoard(int boardId) {
        var board = dao.getBoardDao().get(boardId);

        for (var column : dao.getColumnDao().get_all(boardId)) {
            board.addColumn(column);

            for (var card : dao.getCardDao().get_all(column.getId())) {
                column.addCard(card);
            }
        }
        return board;
    }
}
