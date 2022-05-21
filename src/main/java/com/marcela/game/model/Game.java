package com.marcela.game.model;

import lombok.Getter;
import lombok.Setter;
import com.marcela.utils.Randomizer;

import java.util.List;

@Getter
@Setter
public class Game {
    private Board board;
    private int bombs;
    private final double bombDensity = 0.2;

    public Game(int height, int width) {
        this.board = createBoard(height, width);
        this.bombs = calculateBombs();
        populateBoard();
        placeBombs();
        board.evaluateNeighbourTiles();
    }

    private Board createBoard(int height, int width) {
        return new Board(height, width);
    }

    private int calculateBombs() {
        return (int) (board.getHeight() * board.getWidth() * bombDensity);
    }

    private void populateBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                final var location = new Location(i, j);
                final var tile = new Tile(location);
                board.getPlayArea()[i][j] = tile;
                board.getListOfTiles().add(tile);
            }
        }
    }

    private void placeBombs() {
        List<Tile> listOfTiles = board.getListOfTiles();
        int bombsLeft = this.bombs;
        while (bombsLeft > 0) {
            int randomTileIndex = Randomizer.getRandomNumberFromRange(0, listOfTiles.size() - 1);
            Tile selectedTile = listOfTiles.get(randomTileIndex);
            if (!selectedTile.hasBomb()) {
                selectedTile.setHasBomb(true);
                bombsLeft--;
            }
        }
    }

    public boolean areAllTilesRevealed() {
        return board.getListOfTiles().size() - board.getRevealedTiles() - bombs == 0;
    }

    public void resetBoard() {
        getBoard().getListOfTiles()
                .forEach(tile -> {
                    tile.setRevealed(false);
                    tile.setFlagged(false);
                });
    }

    public int getBombDisplay() {
        int markedBombs = getBoard().getListOfTiles()
                .stream()
                .filter(Tile::isFlagged)
                .mapToInt(tile -> 1)
                .sum();
        return bombs - markedBombs;
    }
}
