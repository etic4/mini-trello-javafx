package model;

public class TrelloFacade {
    private final DaoFactory dao;
    private BoardFacade boardFacade;

    public TrelloFacade() {
        this.dao = new DaoFactory();
    }

    public BoardFacade getBoardFacade() {
        if (boardFacade == null) {
            var board = loadBoard(1);
            boardFacade = new BoardFacade(board);
        }

        return boardFacade;
    }

    public void seedData() {
        dao.seedData();
    }

    private Board loadBoard(int boardId) {
        var board = dao.getBoardDao().get(boardId);

        for (var column : dao.getColumnDao().get_all(boardId)) {
            column.setInBoard(board);

            for (var card : dao.getCardDao().get_all(column.getId())) {
                card.setInColumn(column, card.getPosition());
            }
        }
        return board;
    }
}
