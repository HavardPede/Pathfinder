package com.visualiser.pathfinder.algorithms.astar;

import com.visualiser.pathfinder.Entities.Coordinate;
import com.visualiser.pathfinder.interfaces.Scorer;

public class NodeScorer implements Scorer<Coordinate> {
    @Override
    public int computeCost(Coordinate from, Coordinate to) {
        return axisDistance(from.getX(), to.getX()) + axisDistance(from.getY(), to.getY());
    }

    private int axisDistance(int from, int to) {
        return Math.max(from, to) - Math.min(from, to);
    }
}
