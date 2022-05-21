package com.marcela.game.controller;

import com.marcela.game.model.Location;
import com.marcela.utils.InputScanner;
import com.marcela.utils.Randomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class IntegrationTest {
    InputScanner mockInput;
    Randomizer mockRandomizer;

    @BeforeEach
    void testsSetup() {
        mockInput = Mockito.mock(InputScanner.class);
        mockRandomizer = Mockito.mock(Randomizer.class);
    }

    @Test
    void whenPlayerIsNotHittingABomb_playerMakesAMove() {
        //given
        when(mockInput.getBoardSizeInput(anyString())).thenReturn(3);

        var game = new GameController(mockInput);
        game.initiateGame(mockRandomizer);

        //when
        game.executeRevealMove(xy(1,1));

        //then
        assertThat(game.getGame().getPlayer().isAlive()).isTrue();
        assertThat(game.getGame().getBoard().getTile(xy(1,1)).isRevealed()).isTrue();
    }

    @Test
    void whenPlayerHitsABomb_PlayerExplodes() {
        //given
        when(mockInput.getBoardSizeInput(anyString())).thenReturn(3);

        var game = new GameController(mockInput);
        game.initiateGame(mockRandomizer);

        //when
        game.executeRevealMove(xy(0,0));

        //then
        assertThat(game.getGame().getPlayer().isAlive()).isFalse();
        assertThat(game.getGame().getBoard().getTile(xy(0,0)).isRevealed()).isTrue();
    }

    private static Location xy(int x, int y) {
        return new Location(x, y);
    }
}