package game.model;

import game.controller.LocationController;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Board {
    private final int height;
    private final int width;
    private boolean areAllTilesRevealed;
    private int revealedTiles;
    private LocationController controller;
    private final Tile[][] playArea;
    private final List<Tile> listOfTiles;
    private final Map<String, Location> boardCoordinates;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.areAllTilesRevealed = false;
        this.revealedTiles = 0;
        this.controller = new LocationController();
        this.playArea = new Tile[this.height][this.width];
        this.listOfTiles = new ArrayList<>();
        this.boardCoordinates = populateBoardCoordinates();
    }

    private Map<String, Location> populateBoardCoordinates() {
        final var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        final var digits = new int[this.height];
        for (int i = 0; i < height; i++) {
            digits[i] = i;
        }

        HashMap<String, Location> coordinates = new HashMap<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                coordinates.put(alphabet[j] + String.valueOf(digits[i] + 1), new Location(i, j));
            }
        }
        return coordinates;
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
        final var neighbourTiles = currentTile.getNeighbourTiles();
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

    public void addRevealedTile() {
        this.revealedTiles++;
    }
}
