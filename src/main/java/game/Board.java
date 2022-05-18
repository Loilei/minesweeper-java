package game;

public class Board {
    Bomb[][] board;

    public Board(Bomb[][] board, int boardSize) {
        this.board = new Bomb[boardSize][boardSize];
    }
}
