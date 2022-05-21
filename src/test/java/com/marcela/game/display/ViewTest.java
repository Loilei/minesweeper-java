package com.marcela.game.display;

import com.marcela.game.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final View view = new View();

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
    }

    @Test
    void whenPrintMessageIsCalled_displayTheMessage() {
        //GIVEN
        var expectedMessage = "Message on display";
        //WHEN
        view.printMessage(expectedMessage);
        //THEN
        assertEquals(expectedMessage, outputStreamCaptor.toString().trim());
    }

    @Test
    void whenWelcomeMessagePrintIsCalled_displayTheMessage() {
        //GIVEN
        var expectedMessage = "Welcome to Minesweeper!\n Wanna play a game?\n";
        //WHEN
        view.printMessage(expectedMessage);
        //THEN
        assertEquals(expectedMessage.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void whenWelcomePlayerMessagePrintIsCalled_displayTheMessage() {
        //GIVEN
        var player = new Player();
        player.setName("Alice");
        var expectedMessage = "\nHello " + player.getName();
        //WHEN
        view.printMessage(expectedMessage);
        //THEN
        assertEquals(expectedMessage.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void whenPrintBombsLeftIsCalled_displayTheMessage() {
        //GIVEN
        var numberOfBombs = 5;
        var expectedMessage = "Number of bombs: " + numberOfBombs;
        //WHEN
        view.printMessage(expectedMessage);
        //THEN
        assertEquals(expectedMessage.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void whenPrintVictoryMessageIsCalled_displayTheMessage() {
        //GIVEN
        var expectedMessage = "Congratulations! Patron would be proud!";
        //WHEN
        view.printMessage(expectedMessage);
        //THEN
        assertEquals(expectedMessage.trim(), outputStreamCaptor.toString().trim());
    }
}