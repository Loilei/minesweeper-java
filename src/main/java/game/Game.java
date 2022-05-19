package game;

import lombok.Getter;
import lombok.Setter;
import utils.Randomizer;

import java.util.List;

@Getter
@Setter
public class Game {
    private Board board;
    private int bombs;

    public Game(int height, int width) {
        createBoard(height, width);
        calculateBombs();
        populateBoard();
        placeBombs();
    }

    private void placeBombs() {
        List<Tile> listOfTiles = board.getListOfTiles();
        while (bombs > 0) {
            int randomTileIndex = Randomizer.getRandomNumberFromRange(0, listOfTiles.size() - 1);
            Tile selectedTile = listOfTiles.get(randomTileIndex);
            if (!selectedTile.hasBomb()) {
                selectedTile.setHasBomb(true);
                bombs--;
            }
        }
    }

    private void populateBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                Location location = new Location(i, j);
                Tile tile = new Tile(location);
                board.getPlayArea()[i][j] = tile;
                board.getListOfTiles().add(tile);
            }
        }
    }

    private void calculateBombs() {
        double bombDensity = 0.3;
        this.bombs = (int) (board.getHeight() * board.getWidth() * bombDensity);
    }

    private void createBoard(int height, int width) {
        this.board = new Board(height, width);


    }
}
