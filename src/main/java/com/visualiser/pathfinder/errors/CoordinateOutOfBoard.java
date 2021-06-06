package com.visualiser.pathfinder.errors;

import com.visualiser.pathfinder.Entities.Coordinate;

public class CoordinateOutOfBoard extends Exception {
    public CoordinateOutOfBoard(Coordinate coord, Coordinate boardSize) {
        super("The coordinate "
                + coord.toString()
                + "does not fit on a board of size"
                + boardSize.getX() + "x" + boardSize.getY()
        );
    }
}
