package com.marcela;

import com.marcela.game.controller.GameController;
import com.marcela.utils.Randomizer;

public class Main {
    public static void main(String[] args) {
        GameController game = new GameController();
        game.initiateGame(new Randomizer());
        game.runGame();
        game.playAgain();
    }
}
