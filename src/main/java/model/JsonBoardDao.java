package model;

import java.util.List;

class JsonBoardDao implements Dao<Board> {

    @Override
    public Board get(int id) {
        return null;
    }

    @Override
    public List<Board> get_all(int i) {
        throw new UnsupportedOperationException("Il n'y a qu'un board");
    }

    @Override
    public Board save(Board board) {
        throw new UnsupportedOperationException("Il n'y a qu'un board");
    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void updatePositions(List<Board> boards) {
        throw new UnsupportedOperationException("Un board n'a pas de position");
    }

    @Override
    public void delete(Board board) {
        throw new UnsupportedOperationException("Il n'y a qu'un board");
    }
}
