package model;


public class TrelloFacade {
    private final DaoFactory dao;

    public TrelloFacade() {
        this.dao = new DaoFactory(DaoBackendType.SQLITE);
    }

    public BoardFacade getBoardFacade() {
        var board = dao.getBoardDao().get(1);
        return new BoardFacade(board);
    }
}
