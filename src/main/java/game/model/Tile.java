package game.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tile {
    private Location location;
    private boolean hasBomb;
    private boolean isFlagged;
    private boolean isRevealed;
    private List<Tile> neighbourTiles;

    public Tile(Location location) {
        this.location = location;
        this.hasBomb = false;
        this.isFlagged = false;
        this.isRevealed = false;
        this.neighbourTiles = new ArrayList<>();
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public int getNumberOfNeighbourBombs() {
        return neighbourTiles.stream()
                .filter(Tile::hasBomb)
                .mapToInt(tile -> 1)
                .sum();
    }
}
