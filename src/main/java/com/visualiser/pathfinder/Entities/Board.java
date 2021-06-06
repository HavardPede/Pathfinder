package com.visualiser.pathfinder.Entities;

import com.visualiser.pathfinder.errors.CoordinateOutOfBoard;

public class Board {
    private Coordinate size;
    private Coordinate start;
    private Coordinate end;
    private Coordinate[] walls;

    public Board(Coordinate start, Coordinate end, Coordinate size, Coordinate[] walls) throws CoordinateOutOfBoard {
        this.size = size;
        this.walls = walls;

        if(isCoordinateOutOfBoard(start)) {
            throw new CoordinateOutOfBoard(start, this.size);
        }
        if(isCoordinateOutOfBoard(end)) {
            throw new CoordinateOutOfBoard(end, this.size);
        }
        this.start = start;
        this.end = end;
    }

    public boolean isValid() {
        boolean valid = !(isCoordinateOutOfBoard(start) || isCoordinateOutOfBoard(end));
        for(Coordinate wall : walls) {
            if(isCoordinateOutOfBoard(wall)) valid = false;
        }
        return valid;
    }

    private boolean isCoordinateOutOfBoard(Coordinate coord) {
        return coord.getX() < 0 || coord.getX() >= this.size.getX()
                || coord.getY() < 0 || coord.getY() >= this.size.getY();
    }

    public Coordinate getSize() {
        return size;
    }

    public void setSize(Coordinate size) {
        this.size = size;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }

    public Coordinate[] getWalls() {
        return walls;
    }

    public void setWalls(Coordinate[] walls) {
        this.walls = walls;
    }
}
