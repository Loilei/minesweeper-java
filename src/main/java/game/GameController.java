package game;

import display.BoardDisplay;
import display.View;

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
        view.printMessage("Choose map height (minimum 5, maximum 10): ");
        int height = inputScanner.getIntInput();
        view.printMessage("Choose map width (minimum 5, maximum 10): ");
        int width = inputScanner.getIntInput();
        this.game = new Game(height, width);

        boardDisplay.printBoard(game.getBoard());

        while (game.getBoard().areAllTilesRevealed() || player.isAlive()) {
            String chosenCoordinates = "";
            view.printMessage("Choose a coordinates of a tile to reveal it or place a flag (example: B3):");
            do {
                chosenCoordinates = inputScanner.getStringInput();
                if (!areCoordinatesValid(chosenCoordinates)) {
                    view.printMessage("Coordinates are not valid");
                }
            }
            while (!areCoordinatesValid(chosenCoordinates));

            view.printMessage("Press R to reveal the tile. Press F to place or remove a flag.");
            String action = inputScanner.getStringInput();
        }
    }

    private boolean areCoordinatesValid(String chosenCoordinates) {
        return game.getBoard().getBoardCoordinates().containsKey(chosenCoordinates);
    }


}
