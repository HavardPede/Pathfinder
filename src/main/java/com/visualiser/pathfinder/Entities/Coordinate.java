package com.visualiser.pathfinder.Entities;

import com.visualiser.pathfinder.algorithms.astar.Helper;
import com.visualiser.pathfinder.interfaces.GraphNode;

public class Coordinate implements GraphNode {
    private final String id;
    private final String name;
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.id = Helper.createName(x, y);
        this.name = "{x: " + x + ", y: " + y + "}";
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Coordinate coordinate) {
        return coordinate.getX() == x && coordinate.getY() == y;
    }

    @Override
    public String toString() {
        return name;
    }
}

