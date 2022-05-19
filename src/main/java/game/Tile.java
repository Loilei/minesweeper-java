package game;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Tile {
    private Location location;
    private boolean hasBomb;
    private boolean isRevealed;
    private List<Tile> neighbourTiles;

    public Tile(Location location) {
        this.location = location;
        this.hasBomb = false;
        this.isRevealed = false;
        this.neighbourTiles = new ArrayList<>();
    }

    public boolean hasBomb() {
        return hasBomb;
    }

}
