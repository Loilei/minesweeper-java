package game;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Board {
    private final int height;
    private final int width;
    private LocationController controller;
    private final Tile[][] playArea;
    private final List<Tile> listOfTiles;


    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.controller = new LocationController();
        this.playArea = new Tile[this.height][this.width];
        this.listOfTiles = new ArrayList<>();
    }

    public Tile getTile(Location location) {
        return playArea[location.getX()][location.getY()];
    }

    public void evaluateNeighbourTiles() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Location currentLocation = new Location(i, j);
                Tile currentTile = getTile(currentLocation);
                getTileNeighbours(currentLocation, currentTile);
            }
        }
    }

    private void getTileNeighbours(Location currentLocation, Tile currentTile) {
        List<Tile> neighbourTiles = currentTile.getNeighbourTiles();
        try {
            neighbourTiles.add(getTile(controller.getNorthLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getNorthEastLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getEastLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getSouthEastLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getSouthLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getSouthWestLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getWestLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(getTile(controller.getNorthWestLocation(currentLocation)));
        } catch (Exception ignored) {
        }
    }
}
