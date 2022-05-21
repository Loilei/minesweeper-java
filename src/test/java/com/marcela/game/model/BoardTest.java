package com.marcela.game.model;

import com.marcela.game.model.enums.RevealStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void testsSetup() {
        this.board = new Board(4, 4);
        setTilesInBoard();
    }

    @Test
    void whenCreatingBoard_boardIsCreatedProperly() {
        var board = new Board(4,4);

        assertEquals(4, board.getHeight());
        assertEquals(4, board.getWidth());
        assertEquals(0, board.getRevealedTiles());
        assertEquals(0, board.getListOfTiles().size());
        assertEquals(Collections.emptyList(), board.getListOfTiles());
        assertTrue(board.getBoardCoordinates().size() > 0);
        assertTrue(board.getPlayArea()[0].length > 0);
    }

    @Test
    void whenCreatingBoard_fieldsAreNotNull() {
        var board = new Board(4,4);

        assertNotNull(board.getPlayArea());
        assertNotNull(board.getBoardCoordinates());
        assertNotNull(board.getListOfTiles());
    }

    @Test
    void whenPopulateBoardCoordinates_populateHashMapCoordinates() {
        //GIVEN
        var boardCoordinates = board.getBoardCoordinates();
        //WHEN
        //THEN
        assertEquals(16, boardCoordinates.size());
        assertEquals(new Location(0,0), boardCoordinates.get("A1"));
        assertTrue(boardCoordinates.containsKey("A1"));
        assertEquals(new Location(1,2), boardCoordinates.get("C2"));
        assertTrue(boardCoordinates.containsKey("C3"));
    }

    @Test
    void whenGetTileIsCalled_correctTileIsReturned () {
        //GIVEN
        var tile = new Tile(new Location(1,1));
        //WHEN
        var boardTile = board.getTile(new Location(1, 1));
        //THEN
        assertEquals(tile, boardTile);
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