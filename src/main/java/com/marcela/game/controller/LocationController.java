package com.marcela.game.controller;

import com.marcela.game.model.Location;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocationController {

    public Location getNorthLocation(Location location) {
        return new Location(location.x() - 1, location.y());
    }

    public Location getNorthEastLocation(Location location) {
        return new Location(location.x() - 1, location.y() + 1);
    }

    public Location getEastLocation(Location location) {
        return new Location(location.x(), location.y() + 1);
    }

    public Location getSouthEastLocation(Location location) {
        return new Location(location.x() + 1, location.y() + 1);
    }

    public Location getSouthLocation(Location location) {
        return new Location(location.x() + 1, location.y());
    }

    public Location getSouthWestLocation(Location location) {
        return new Location(location.x() + 1, location.y() - 1);
    }

    public Location getWestLocation(Location location) {
        return new Location(location.x(), location.y() - 1);
    }

    public Location getNorthWestLocation(Location location) {
        return new Location(location.x() - 1, location.y() - 1);
    }
}
