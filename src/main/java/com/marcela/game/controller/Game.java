package com.marcela.game.controller;

import com.marcela.game.model.Board;
import com.marcela.game.model.Location;
import com.marcela.game.model.Player;
import com.marcela.game.model.Tile;
import lombok.Getter;
import lombok.Setter;
import com.marcela.utils.Randomizer;

import java.util.List;

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
                final var currentTileNeighbours = currentTile.getNeighbourTiles();
                if (i == 0) {
                    if (j == 0) {
                        getTopLeftCornerNeighbours(currentLocation, currentTileNeighbours);
                    } else if (j == (board.getWidth() - 1)) {
                        getTopRightCornerNeighbours(currentLocation, currentTileNeighbours);
                    } else {
                        getTopBorderNeighbours(currentLocation, currentTileNeighbours);
                    }
                } else if (i == (board.getHeight() - 1)) {
                    if (j == 0) {
                        getBottomLeftCornerNeighbours(currentLocation, currentTileNeighbours);
                    } else if (j == board.getWidth() - 1) {
                        getBottomRightCornerNeighbours(currentLocation, currentTileNeighbours);
                    } else {
                        getBottomBorderNeighbours(currentLocation, currentTileNeighbours);
                    }
                } else {
                    if (j == 0) {
                        getLeftBorderNeighbours(currentLocation, currentTileNeighbours);
                    } else if (j == (board.getWidth() - 1)) {
                        getRightBorderNeighbours(currentLocation, currentTileNeighbours);
                    } else {
                        getMiddleTileNeighbours(currentLocation, currentTileNeighbours);
                    }
                }
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
        final var revealedTiles = getRevealedTiles();
        return board.getListOfTiles().size() - revealedTiles - bombs == 0;
    }

    public int getRevealedTiles() {
        final var sum = board.getListOfTiles().stream()
                .filter(Tile::isRevealed)
                .mapToInt(tile -> 1)
                .sum();
        return sum;
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

    private void getMiddleTileNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthWestLocation(currentLocation)));
    }

    private void getRightBorderNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
    }

    private void getLeftBorderNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
    }

    private void getTopBorderNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
    }

    private void getBottomBorderNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
    }

    private void getBottomRightCornerNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
    }

    private void getBottomLeftCornerNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getNorthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getNorthEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
    }

    private void getTopRightCornerNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthWestLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getWestLocation(currentLocation)));
    }

    private void getTopLeftCornerNeighbours(Location currentLocation, List<Tile> neighbourTiles) {
        neighbourTiles.add(board.getTile(locationController.getEastLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthLocation(currentLocation)));
        neighbourTiles.add(board.getTile(locationController.getSouthEastLocation(currentLocation)));
    }

    public Board getBoard() {
        return board;
    }
}
