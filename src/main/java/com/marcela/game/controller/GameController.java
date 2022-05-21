package com.marcela.game.controller;

import com.marcela.game.display.BoardDisplay;
import com.marcela.game.display.View;
import com.marcela.game.model.*;
import com.marcela.utils.InputScanner;
import com.marcela.utils.ScreenMaintenance;

import java.util.Arrays;

public class GameController {
    private Game game;
    private Player player;
    private final View view;
    private final BoardDisplay boardDisplay;
    private final InputScanner inputScanner;

    public GameController() {
        this.view = new View();
        this.boardDisplay = new BoardDisplay();
        this.inputScanner = new InputScanner();
    }

    public void start() {
        view.printWelcomeMessage();
        this.player = createPlayer();
        this.game = createMap();
        view.printMessage("Let the com.marcela.game begin!\n");
        runGame();
        playAgain();
    }

    private void runGame() {
        while (!isGameOver()) {
            printUi();
            final var chosenCoordinates = getCoordinatesFromUser();
            final var chosenAction = getAction();
            final var tile = getChosenTile(chosenCoordinates);
            playSingleRound(chosenAction, tile);
            if (hasWon()) {
                view.printVictoryMessage();
                break;
            }
        }
    }

    private void playSingleRound(Action chosenAction, Tile tile) {
        if (chosenAction.equals(Action.FLAG)) {
            executeFlagMove(tile);
        } else if (chosenAction.equals(Action.REVEAL)) {
            executeRevealMove(tile);
        }
    }

    private void printUi() {
        view.printBombsLeft(game.getBombDisplay());
        boardDisplay.printBoard(game.getBoard());
    }

    private Game createMap() {
        view.printWelcomePlayerMessage(player.getName());
        final var height = inputScanner.getBoardSizeInput("Choose map height (minimum 5, maximum 10) and hit Enter: ");
        final var width = inputScanner.getBoardSizeInput("Choose map width (minimum 5, maximum 10) and hit Enter: ");
        return new Game(height, width);
    }

    private Player createPlayer() {
        view.printMessage("Please type your name: ");
        final var player = new Player();
        player.setName(inputScanner.getStringInput());
        return player;
    }

    private void playAgain() {
        view.printMessage("Do you want to play again? [Y/N]: ");
        final var options = Arrays.asList("Y", "N");
        final var playAgain = inputScanner.getLimitedInput(options);
        if (playAgain.equals("Y")) {
            ScreenMaintenance.clearScreen();
            start();
        } else {
            ScreenMaintenance.quitGame();
        }
    }

    private void executeRevealMove(Tile tile) {
        if (tile.isRevealed()) {
            view.printMessage("Tile already revealed. Choose another coordinates");
        } else if (tile.hasBomb()) {
            endGame(tile);
            restartGame();
        } else {
            tile.setRevealed(true);
            game.getBoard().addRevealedTile();
            revealNeighbourEmptyTiles(tile);
        }
    }

    private void restartGame() {
        view.printMessage("\nYou have died " + player.getName() + ". Your com.marcela.game has been restarted with same bomb placement.\n");
        game.resetBoard();
        player.setAlive(true);
    }

    private void endGame(Tile tile) {
        player.setAlive(false);
        tile.setRevealed(true);
        view.printMessage("YOU DIED!\n");
        boardDisplay.printBoard(game.getBoard());
    }

    private void revealNeighbourEmptyTiles(Tile tile) {
        final var neighbourTiles = tile.getNeighbourTiles();
        neighbourTiles.stream()
                .filter(neighbour -> !neighbour.hasBomb())
                .filter(neighbour -> neighbour.getNumberOfNeighbourBombs() == 0)
                .forEach(neighbour -> {
                    neighbour.setRevealed(true);
                    game.getBoard().addRevealedTile();
                });
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
        final var boardCoordinates = game.getBoard().getBoardCoordinates();
        final var location = boardCoordinates.get(chosenCoordinates);
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

    private String getCoordinatesFromUser() {
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
        return game.areAllTilesRevealed() || !player.isAlive();
    }

    private boolean hasWon() {
        return game.areAllTilesRevealed() && player.isAlive();
    }

    private boolean areCoordinatesValid(String chosenCoordinates) {
        return game.getBoard().getBoardCoordinates().containsKey(chosenCoordinates);
    }
}