package core;

import core.model.Board;

public class World {
    private final Board board;

    public World() {
        this.board = new Board(60, 30);
        board.generateRooms(); // Now generates rooms based on density
    }

    public Board getBoard() {
        return board;
    }
}


