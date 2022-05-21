package com.marcela.game.controller;

import com.marcela.game.model.Board;
import com.marcela.game.model.Location;
import com.marcela.game.model.RevealResult;
import com.marcela.game.model.Tile;
import com.marcela.game.model.enums.RevealStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {
    private Board board;

    @BeforeEach
    void testsSetup() {
        this.board = new Board(4, 4);
        setTilesInBoard();
    }

    @Test
    void whenPlayerRevealsBomb_playerExplodes() {
        //GIVEN
        var userChosenLocation = new Location(1, 1);
        //WHEN
        board.getTile(userChosenLocation).setHasBomb(true);
        RevealResult revealResult = board.revealTile(userChosenLocation);
        //THEN
        assertEquals(RevealStatus.EXPLODED, revealResult.getStatus());
    }

    private void setTilesInBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                final var location = new Location(i, j);
                final var tile = new Tile(location);
                board.getPlayArea()[i][j] = tile;
                board.getListOfTiles().add(tile);
            }
        }
    }
}