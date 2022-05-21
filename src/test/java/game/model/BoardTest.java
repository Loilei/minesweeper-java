package game.model;

import game.controller.LocationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private LocationController locationController;

    @BeforeEach
    void testsSetup() {
        this.board = new Board(4, 4);
        this.locationController = new LocationController();
        setTilesInBoard();
    }

    @Test
    void whenCreatingBoard_boardIsCreatedProperly() {
        //GIVEN
        var board = new Board(4,4);
        //WHEN
        //THEN
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
        //GIVEN
        //WHEN
        //THEN
        assertNotNull(board);
        assertNotNull(board.getPlayArea());
        assertNotNull(board.getBoardCoordinates());
        assertNotNull(board.getListOfTiles());
        assertNotNull(board.getLocationController());
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
        Tile boardTile = board.getTile(new Location(1, 1));
        //THEN
        assertEquals(tile, boardTile);
    }

    @Test
    void whenEvaluateNeighbourTilesIsCalled_tileNeighboursArePopulated () {
        //GIVEN
        Location location = new Location(1, 1);
        Tile tile = board.getTile(location);
        Tile neighbour1 = board.getTile(locationController.getNorthLocation(location));
        Tile neighbour2 = board.getTile(locationController.getNorthEastLocation(location));
        Tile neighbour3 = board.getTile(locationController.getEastLocation(location));
        Tile neighbour4 = board.getTile(locationController.getSouthEastLocation(location));
        Tile neighbour5 = board.getTile(locationController.getSouthLocation(location));
        Tile neighbour6 = board.getTile(locationController.getSouthWestLocation(location));
        Tile neighbour7 = board.getTile(locationController.getWestLocation(location));
        Tile neighbour8 = board.getTile(locationController.getNorthWestLocation(location));
        var listOfNeighbours = Arrays.asList(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        //WHEN
        board.evaluateNeighbourTiles();
        //THEN
        assertEquals(listOfNeighbours, tile.getNeighbourTiles());
        assertEquals(8, tile.getNeighbourTiles().size());
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