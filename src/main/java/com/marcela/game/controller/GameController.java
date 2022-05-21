package com.marcela.game.controller;

import com.marcela.game.display.BoardDisplay;
import com.marcela.game.display.View;
import com.marcela.game.model.*;
import com.marcela.game.model.enums.Action;
import com.marcela.game.model.enums.RevealStatus;
import com.marcela.utils.InputScanner;
import com.marcela.utils.Randomizer;
import com.marcela.utils.ScreenMaintenance;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class GameController {
    private Game game;
    private final View view;
    private final BoardDisplay boardDisplay;
    private final InputScanner inputScanner;

    public GameController() {
        this.view = new View();
        this.boardDisplay = new BoardDisplay();
        this.inputScanner = new InputScanner();
    }

    public GameController(InputScanner scanner) {
        this.view = new View();
        this.boardDisplay = new BoardDisplay();
        this.inputScanner = scanner;
    }

    public void initiateGame(Randomizer randomizer) {
        view.printWelcomeMessage();
        this.game = createMap(randomizer);
        setPlayersName();
        view.printMessage("Let the game begin!\n");
    }

    public void runGame() {
        while (!isGameOver()) {
            printUi();
            final var chosenCoordinates = getCoordinatesFromUser();
            final var chosenAction = getActionFromUser();
            final var location = getChosenLocation(chosenCoordinates);
            playSingleRound(chosenAction, location);
            if (hasWon()) {
                view.printVictoryMessage();
                break;
            }
            if (!game.isPlayerAlive()) {
                restartGame();
            }

        }
    }

    private void playSingleRound(Action chosenAction, Location location) {
        if (chosenAction.equals(Action.FLAG)) {
            executeFlagMove(location);
        } else if (chosenAction.equals(Action.REVEAL)) {
            executeRevealMove(location);
        }
    }

    private void printUi() {
        view.printBombsLeft(game.getBombDisplay());
        boardDisplay.printBoard(game.getBoard());
    }

    private Game createMap(Randomizer randomizer) {
        final var height = inputScanner.getBoardSizeInput("Choose map height (minimum 5, maximum 10) and hit Enter: ");
        final var width = inputScanner.getBoardSizeInput("Choose map width (minimum 5, maximum 10) and hit Enter: ");
        return new Game(height, width, randomizer);
    }

    private void setPlayersName() {
        view.printMessage("Please type your name: ");
        game.getPlayer().setName(inputScanner.getStringInput());
        view.printWelcomePlayerMessage(game.getPlayer().getName());
    }

    public void playAgain() {
        view.printMessage("Do you want to play again? [Y/N]: ");
        final var options = Arrays.asList("Y", "N");
        final var playAgain = inputScanner.getLimitedInput(options);
        if (playAgain.equals("Y")) {
            ScreenMaintenance.clearScreen();
            initiateGame(new Randomizer());
            runGame();
        } else {
            ScreenMaintenance.quitGame();
        }
    }

    public void executeRevealMove(Location location) {
        try {
            final var revealResult = game.getBoard().revealTile(location);
            if (revealResult.getStatus().equals(RevealStatus.EXPLODED)) {
                endGame(location);
            } else if (revealResult.getStatus().equals(RevealStatus.OK)) {
                final var tile = game.getBoard().getTile(location);
                tile.setRevealed(true);
                revealNeighbourEmptyTiles(tile);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void restartGame() {
        view.printMessage("\nYou have died " + game.getPlayer().getName() + ". Your game has been restarted with same bomb placement.\n");
        game.resetBoard();
        game.resurrectPlayer();
    }

    private void endGame(Location location) {
        game.killPlayer();
        final var tile = game.getBoard().getTile(location);
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
                    if (!neighbour.isRevealed()) {
                        neighbour.setRevealed(true);
                    }
                });
    }

    private void executeFlagMove(Location location) {
        try {
            Tile tile = game.getBoard().getFlaggedTile(location);
            toggleFlagged(tile);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void toggleFlagged(Tile tile) {
        tile.setFlagged(!tile.isFlagged());
    }

    private Location getChosenLocation(String chosenCoordinates) {
        final var boardCoordinates = game.getBoard().getBoardCoordinates();
        return boardCoordinates.get(chosenCoordinates);
    }

    private Action getActionFromUser() {
        String action = "";
        view.printMessage("\nPress R to reveal the tile. Press F to place or remove a flag.");
        do {
            action = inputScanner.getActionStringInput().toUpperCase();
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
        return game.areAllTilesRevealed() || !game.isPlayerAlive();
    }

    private boolean hasWon() {
        return game.areAllTilesRevealed() && game.isPlayerAlive();
    }

    private boolean areCoordinatesValid(String chosenCoordinates) {
        return game.getBoard().getBoardCoordinates().containsKey(chosenCoordinates);
    }
}
