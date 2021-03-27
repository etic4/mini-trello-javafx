package model;

import main.Data;

public class TrelloFacade {
    private final Board board = Data.init();;

    public BoardFacade getBoardFacade() {
        return new BoardFacade(board);
    }
}
