package com.marcela.game.controller;

import com.marcela.game.model.Board;
import com.marcela.game.model.Location;
import com.marcela.game.model.Player;
import com.marcela.game.model.Tile;
import lombok.Getter;
import lombok.Setter;
import com.marcela.utils.Randomizer;

@Getter
@Setter
public class Game {
    private final double bombDensity = 0.2;
    private int bombs;
    private Board board;
    private Player player;
    private final LocationController locationController;
    private Randomizer randomizer;


    public Game(int height, int width, Randomizer randomizer) {
        this.locationController = new LocationController();
        this.board = createBoard(height, width);
        this.bombs = calculateBombs();
        this.player = new Player();
        this.randomizer = randomizer;
        populateBoard();
        placeBombs();
        evaluateNeighbourTiles();
    }

    public Game(Board board) {
        this.locationController = new LocationController();
        this.board = board;
        this.bombs = calculateBombs();
        this.player = new Player();
        this.randomizer = new Randomizer();
        populateBoard();
        placeBombs();
        evaluateNeighbourTiles();
    }

    private Board createBoard(int height, int width) {
        return new Board(height, width);
    }

    private int calculateBombs() {
        return (int) (board.getHeight() * board.getWidth() * bombDensity);
    }

    public void evaluateNeighbourTiles() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                final var currentLocation = new Location(i, j);
                final var currentTile = board.getTile(currentLocation);
                getTileNeighbours(currentLocation, currentTile);
            }
        }
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
        final var listOfTiles = board.getListOfTiles();
        int bombsLeft = this.bombs;
        while (bombsLeft > 0) {
            int randomTileIndex = randomizer.getRandomNumberFromRange(0, listOfTiles.size() - 1);
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
        final var markedBombs = getBoard().getListOfTiles()
                .stream()
                .filter(Tile::isFlagged)
                .mapToInt(tile -> 1)
                .sum();
        return bombs - markedBombs;
    }

    public void killPlayer() {
        player.setAlive(false);
    }

    public void resurrectPlayer() {
        player.setAlive(true);
    }

    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    private void getTileNeighbours(Location currentLocation, Tile currentTile) {
        final var neighbourTiles = currentTile.getNeighbourTiles();
        try {
            neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getNorthEastLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getSouthEastLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getSouthWestLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
        } catch (Exception ignored) {
        }
        try {
            neighbourTiles.add(board.getTile(locationController.getNorthWestLocation(currentLocation)));
        } catch (Exception ignored) {
        }
    }

    public void setRandomizer(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    public Board getBoard() {
        return board;
    }
}
