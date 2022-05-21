package com.marcela.game.controller;

import com.marcela.game.model.Board;
import com.marcela.game.model.Location;
import com.marcela.game.model.Tile;
import com.marcela.utils.Randomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    private LocationController locationController;
    private Game game;

    Board mockBoard = mock(Board.class);

    @BeforeEach
    void testsSetup() {
        this.locationController = new LocationController();
    }

    @Test
    void whenEvaluateNeighbourTilesDuringCreationIsCalled_tileNeighboursArePopulated() {
        //GIVEN
        this.game = new Game(4, 4, new Randomizer());
        Location location = new Location(1, 1);
        var tile = game.getBoard().getTile(location);
        var neighbour1 = game.getBoard().getTile(locationController.getNorthLocation(location));
        var neighbour2 = game.getBoard().getTile(locationController.getNorthEastLocation(location));
        var neighbour3 = game.getBoard().getTile(locationController.getEastLocation(location));
        var neighbour4 = game.getBoard().getTile(locationController.getSouthEastLocation(location));
        var neighbour5 = game.getBoard().getTile(locationController.getSouthLocation(location));
        var neighbour6 = game.getBoard().getTile(locationController.getSouthWestLocation(location));
        var neighbour7 = game.getBoard().getTile(locationController.getWestLocation(location));
        var neighbour8 = game.getBoard().getTile(locationController.getNorthWestLocation(location));
        var listOfNeighbours = Arrays.asList(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        //WHEN
        //THEN
        assertEquals(listOfNeighbours, tile.getNeighbourTiles());
        assertEquals(8, tile.getNeighbourTiles().size());
    }

    @Test
    void whenCreatingGame_gameIsCreatedProperly() {
        //GIVEN
        this.game = new Game(4, 4, new Randomizer());
        //WHEN
        //THEN
        assertEquals(3, game.getBombs());
        assertNotNull(game.getBoard());
        assertNotNull(game.getPlayer());
        assertNotNull(game.getLocationController());
    }

    @Test
    void whenGameIsCreated_boardTilesArePopulated() {
        //GIVEN
        this.game = new Game(4, 4, new Randomizer());
        var location1 = new Location(1, 1);
        var location2 = new Location(0, 2);
        var location3 = new Location(0, 0);
        var location4 = new Location(3, 2);
        var location5 = new Location(0, 1);
        //WHEN
        //THEN
        assertNotNull(game.getBoard().getTile(location1));
        assertNotNull(game.getBoard().getTile(location2));
        assertNotNull(game.getBoard().getTile(location3));
        assertNotNull(game.getBoard().getTile(location4));
        assertNotNull(game.getBoard().getTile(location5));
    }

    @Test
    void whenAllTilesAreRevealed_areAllTilesRevealedReturnsTrue() {
        //GIVEN
        this.game = new Game(mockBoard);
        game.setBombs(2);
        List<Integer> listOfSize16 = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            listOfSize16.add(1);
        }
        //WHEN
        doReturn(listOfSize16).when(mockBoard).getListOfTiles();
        when(mockBoard.getRevealedTiles()).thenReturn(14);
        //THEN
        assertTrue(game.areAllTilesRevealed());
    }

    @Test
    void whenNotAllTilesAreRevealed_areAllTilesRevealedReturnsFalse() {
        //GIVEN
        this.game = new Game(mockBoard);
        game.setBombs(2);
        List<Integer> listOfSize16 = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            listOfSize16.add(1);
        }
        //WHEN
        doReturn(listOfSize16).when(mockBoard).getListOfTiles();
        when(mockBoard.getRevealedTiles()).thenReturn(2);
        //THEN
        assertFalse(game.areAllTilesRevealed());
    }

    @Test
    void whenResetBoard_tilesGetReset() {
        //GIVEN
        this.game = new Game(4, 4, new Randomizer());
        Tile tile1 = game.getBoard().getTile(new Location(1, 1));
        Tile tile2 = game.getBoard().getTile(new Location(1, 2));
        tile1.setRevealed(true);
        tile2.setFlagged(true);
        //WHEN
        game.resetBoard();
        //THEN
        assertFalse(tile1.isRevealed());
        assertFalse(tile2.isFlagged());
    }

    @Test
    void whenKillPlayer_playerIsKilled() {
        //GIVEN
        this.game = new Game(4, 4, new Randomizer());
        //WHEN
        game.killPlayer();
        //THEN
        assertFalse(game.getPlayer().isAlive());
    }

    @Test
    void whenPlayerIsResurrected_isAliveIsSetToTrue() {
        //GIVEN
        this.game = new Game(4, 4, new Randomizer());
        //WHEN
        game.resurrectPlayer();
        //THEN
        assertTrue(game.getPlayer().isAlive());
    }

}