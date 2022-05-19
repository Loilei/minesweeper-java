package game;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Board {
    private final int height;
    private final int width;
    private final Tile[][] playArea;
    private final List<Tile> listOfTiles;


    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.playArea = new Tile[this.height][this.width];
        this.listOfTiles = new ArrayList<>();
    }
}
