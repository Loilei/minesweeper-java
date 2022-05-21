package com.marcela.game.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile tile;

    @Test
    void whenCreatingTile_tileIsCreatedProperly() {
        //GIVEN
        var location =new Location(1,1);
        this.tile = new Tile(location);
        //WHEN
        //THEN
        assertFalse(tile.hasBomb());
        assertEquals(location, tile.getLocation());
        assertFalse(tile.isFlagged());
        assertFalse(tile.isRevealed());
        assertNotNull(tile.getNeighbourTiles());
    }

    @Test
    void whenCallingGetNumberOfNeighbourBombs_returnNumberOfBombs() {
        //GIVEN
        var location =new Location(1,1);
        this.tile = new Tile(location);
        var tileNeighbour1 = new Tile(location);
        var tileNeighbour2 = new Tile(location);
        var tileNeighbour3 = new Tile(location);
        tileNeighbour1.setHasBomb(false);
        tileNeighbour2.setHasBomb(true);
        tileNeighbour3.setHasBomb(true);
        //WHEN
        tile.setNeighbourTiles(Arrays.asList(tileNeighbour1, tileNeighbour2, tileNeighbour3));
        int numberOfNeighbourBombs = tile.getNumberOfNeighbourBombs();
        //THEN
        assertEquals(2, numberOfNeighbourBombs);
    }

}