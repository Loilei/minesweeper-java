package game.controller;

import game.display.BoardDisplay;
import game.display.View;
import game.model.*;
import utils.InputScanner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameController {
    private Game game;
    private Player player;
    private BoardDisplay boardDisplay;
    private View view;
    private InputScanner inputScanner;

    public GameController() {
        this.boardDisplay = new BoardDisplay();
        this.view = new View();
        this.inputScanner = new InputScanner();
    }

    public void start() {
        view.printMessage("Welcome to Minesweeper!\n Wanna play a game?\n");
        view.printMessage("Please type your name: ");
        this.player = new Player();
//        player.setName(inputScanner.getStringInput());
        player.setName("owca");
        view.printMessage("\nHello " + player.getName() + ", please choose your map size ");
//        int height = inputScanner.getBoardSizeInput("Choose map height (minimum 5, maximum 10) and hit Enter: ");
//        int width = inputScanner.getBoardSizeInput("Choose map width (minimum 5, maximum 10) and hit Enter: ");
        int height = 10;
        int width = 10;

        this.game = new Game(height, width);

        view.printMessage("Let the game begin!\n");

        while (!isGameOver()) {
            boardDisplay.printBoard(game.getBoard());
            String chosenCoordinates = getCoordinates();
            Action chosenAction = getAction();
            Tile tile = getChosenTile(chosenCoordinates);
            if (chosenAction.equals(Action.FLAG)) {
                executeFlagMove(tile);
            } else if (chosenAction.equals(Action.REVEAL)) {
                executeRevealMove(tile);
            }
        }
    }

    private void executeRevealMove(Tile tile) {
        if (tile.isRevealed()) {
            view.printMessage("Tile already revealed. Choose another coordinates");
        } else if (tile.hasBomb()) {
            endGame(tile);
        } else {
            tile.setRevealed(true);
            revealNeighbourEmptyTiles(tile);
        }
    }

    private void endGame(Tile tile) {
        player.setAlive(false);
        tile.setRevealed(true);
        view.printMessage("YOU DIED!\n");
        boardDisplay.printBoard(game.getBoard());
    }

    private void revealNeighbourEmptyTiles(Tile tile) {
        List<Tile> neighbourTiles = tile.getNeighbourTiles();
        neighbourTiles.stream()
                .filter(neighbour -> !neighbour.hasBomb())
                .filter(neighbour -> neighbour.getNumberOfNeighbourBombs() == 0)
                .forEach(neighbour -> neighbour.setRevealed(true));
    }

    private void executeFlagMove(Tile tile) {
        if (tile.isRevealed()) {
            view.printMessage("Tile already revealed. Choose another coordinates");
        } else {
            toggleFlagged(tile);
        }
    }

    private void toggleFlagged(Tile tile) {
        tile.setFlagged(!tile.isFlagged());
    }

    private Tile getChosenTile(String chosenCoordinates) {
        Map<String, Location> boardCoordinates = game.getBoard().getBoardCoordinates();
        Location location = boardCoordinates.get(chosenCoordinates);
        return game.getBoard().getTile(location);
    }

    private Action getAction() {
        String action = "";
        view.printMessage("\nPress R to reveal the tile. Press F to place or remove a flag.");
        do {
            action = inputScanner.getStringInput().toUpperCase();
            if (!isActionValid(action)) {
                view.printMessage("Action is not valid. Choose a valid action [R/F]: ");
            }
        } while (!isActionValid(action));

        if (action.equals("R")) {
            return Action.REVEAL;
        } else if (action.equals("F")) {
            return Action.FLAG;
        }
        throw new IllegalArgumentException("Unknown action " + action);
    }

    private boolean isActionValid(String action) {
        final var validActions = Arrays.asList("F", "R");
        return validActions.contains(action);
    }

    private String getCoordinates() {
        String chosenCoordinates = "";
        view.printMessage("\nChoose a coordinates of a tile to reveal it or place a flag (example: B3): ");
        do {
            chosenCoordinates = inputScanner.getStringInput();
            if (!areCoordinatesValid(chosenCoordinates)) {
                view.printMessage("Coordinates are not valid");
            }
        }
        while (!areCoordinatesValid(chosenCoordinates));
        return chosenCoordinates;
    }

    private boolean isGameOver() {
        return game.getBoard().areAllTilesRevealed() || !player.isAlive();
    }

    private boolean areCoordinatesValid(String chosenCoordinates) {
        return game.getBoard().getBoardCoordinates().containsKey(chosenCoordinates);
    }
}
