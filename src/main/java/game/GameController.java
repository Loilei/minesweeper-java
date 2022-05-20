package game;

import display.BoardDisplay;
import display.View;

import java.util.Arrays;
import java.util.List;

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
        player.setName(inputScanner.getStringInput());
        view.printMessage("\nHello " + player.getName() + ", please choose your map size ");
        int height = inputScanner.getBoardSizeInput("Choose map height (minimum 5, maximum 10) and hit Enter: ");
        int width = inputScanner.getBoardSizeInput("Choose map width (minimum 5, maximum 10) and hit Enter: ");

        this.game = new Game(height, width);

        view.printMessage("Let the game begin!\n");
        boardDisplay.printBoard(game.getBoard());

        while (!isGameOver()) {
            String chosenCoordinates = getCoordinates();
            String chosenAction = getAction();
            //TODO reveal tile logic
        }
    }

    private String getAction() {
        String action = "";
        view.printMessage("\nPress R to reveal the tile. Press F to place or remove a flag.");
        do {
            action = inputScanner.getStringInput().toUpperCase();
            if (!isActionValid(action)) {
                view.printMessage("Action is not valid. Choose a valid action [R/F]: ");
            }
        } while (!isActionValid(action));
        return action;
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
