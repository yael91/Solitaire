package moves;

import game.Board;

public class DeckMove implements Move {

    Board board;

    public DeckMove(Board board) {
        this.board = board;
    }

    @Override
    public void move() {
        if(board.discard()) {

        }
    }
}
