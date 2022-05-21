package com.marcela.game.controller;

import com.marcela.game.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationControllerTest {
    private LocationController locationController;

    @BeforeEach
    void testsSetup() {
        this.locationController = new LocationController();
    }

    @Test
    void whenGetNorthLocation_properNorthLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(3,2);
        //WHEN
        var northLocation = locationController.getNorthLocation(startLocation);
        //THEN
        assertNotNull(northLocation);
        assertEquals(expectedLocation, northLocation);
    }

    @Test
    void whenGetNorthEastLocation_properNorthEastLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(4,2);
        //WHEN
        var northEastLocation = locationController.getNorthEastLocation(startLocation);
        //THEN
        assertNotNull(northEastLocation);
        assertEquals(expectedLocation, northEastLocation);
    }

    @Test
    void whenGetEastLocation_properEastLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(4,3);
        //WHEN
        var eastLocation = locationController.getEastLocation(startLocation);
        //THEN
        assertNotNull(eastLocation);
        assertEquals(expectedLocation, eastLocation);
    }

    @Test
    void whenGetSouthEastLocation_properSouthEastLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(4,4);
        //WHEN
        var southEastLocation = locationController.getSouthEastLocation(startLocation);
        //THEN
        assertNotNull(southEastLocation);
        assertEquals(expectedLocation, southEastLocation);
    }

    @Test
    void whenGetSouthLocation_properSouthLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(3,4);
        //WHEN
        var southLocation = locationController.getSouthLocation(startLocation);
        //THEN
        assertNotNull(southLocation);
        assertEquals(expectedLocation, southLocation);
    }

    @Test
    void whenGetSouthWestLocation_properSouthWestLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(2,4);
        //WHEN
        var southWestLocation = locationController.getSouthWestLocation(startLocation);
        //THEN
        assertNotNull(southWestLocation);
        assertEquals(expectedLocation, southWestLocation);
    }

    @Test
    void whenGetWestLocation_properWestLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(2,3);
        //WHEN
        var westLocation = locationController.getWestLocation(startLocation);
        //THEN
        assertNotNull(westLocation);
        assertEquals(expectedLocation, westLocation);
    }

    @Test
    void whenGetNorthWestLocation_properNorthWestLocationIsReturned() {
        //GIVEN
        var startLocation = new Location(3,3);
        var expectedLocation = new Location(2,2);
        //WHEN
        var northWestLocation = locationController.getNorthWestLocation(startLocation);
        //THEN
        assertNotNull(northWestLocation);
        assertEquals(expectedLocation, northWestLocation);
    }
}