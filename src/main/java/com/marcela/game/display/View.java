package com.marcela.game.display;

public class View {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printWelcomeMessage() {
        printMessage("Welcome to Minesweeper!\n Wanna play a game?\n");
    }

    public void printWelcomePlayerMessage(String playerName){
        printMessage("\nHello " + playerName + ", please choose your map size ");
    }

    public void printBombsLeft(int numberOfBombs) {
        printMessage("Number of bombs: " + numberOfBombs);
    }

    public void printVictoryMessage() {
        printMessage("Congratulations! Patron would be proud!");
    }
}
