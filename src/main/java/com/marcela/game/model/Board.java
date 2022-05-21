package com.marcela.game.model;

import com.marcela.game.model.enums.RevealStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Board {
    private final int height;
    private final int width;
    private int revealedTiles;
    private final List<Tile> listOfTiles;
    private final Map<String, Location> boardCoordinates;
    private final Tile[][] playArea;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.revealedTiles = 0;
        this.playArea = new Tile[this.height][this.width];
        this.listOfTiles = new ArrayList<>();
        this.boardCoordinates = populateBoardCoordinates();
    }

    public RevealResult revealTile(Location location) {
        if (getTile(location).isRevealed()){
            throw new IllegalArgumentException("Tile already revealed. Choose another coordinates.");
        }
        final var tile = getTile(location);
        int surroundingBombs = tile.getNumberOfNeighbourBombs();
        final var revealResult = tile.hasBomb() ? new RevealResult(RevealStatus.EXPLODED, surroundingBombs) : new RevealResult(RevealStatus.OK, surroundingBombs);

        return revealResult;
    }

    public Tile getFlaggedTile(Location location) {
        if (getTile(location).isRevealed()){
            throw new IllegalArgumentException("Tile already revealed. Choose another coordinates.");
        }
        return getTile(location);
    }

    public Tile getTile(Location location) {
        return playArea[location.x()][location.y()];
    }

    public void addRevealedTile() {
        this.revealedTiles++;
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
}
