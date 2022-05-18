package game;

import display.BoardDisplay;
import display.View;

public class GameController {
    private Game game;
    private Player player;
    private BoardDisplay boardDisplay;
    private View view;

    public GameController() {
        this.game = new Game();
        this.boardDisplay = new BoardDisplay();
        this.view = new View();
        this.player = new Player();
    }

    public void start(){
        view.printMessage("Welcome to Minesweeper!\n Wanna play a game?\n");
    }
}
