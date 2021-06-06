package com.visualiser.pathfinder.algorithms.astar;

import com.visualiser.pathfinder.Entities.Coordinate;

import java.util.*;

public class Helper {
    public static Set<Coordinate> generateNodes(Coordinate boardSize) {
        Set<Coordinate> result = new HashSet<>();

        for(int x = 0; x < boardSize.getX(); x++) {
            for(int y = 0; y < boardSize.getY(); y++) {
                result.add(new Coordinate(x, y));
            }
        }

        return result;
    }

    public static Map<String, Set<String>> generateConnections(Coordinate boardSize) {
        Map<String, Set<String>> result = new HashMap<>();

        for(int x = 0; x < boardSize.getX(); x++) {
            for(int y = 0; y < boardSize.getY(); y++) {
                result.put(createName(x, y), generateNeighbours(boardSize, x, y));
            }
        }
        return result;
    }

    private static Set<String> generateNeighbours(Coordinate boardSize, int x, int y) {
        Set<String> neighbours = new HashSet<>();
        if (x > 0) neighbours.add(createName(x - 1, y));
        if (y > 0) neighbours.add(createName(x, y - 1));
        if (x < boardSize.getX() - 1) neighbours.add(createName(x + 1, y));
        if (y < boardSize.getY() - 1) neighbours.add(createName(x, y + 1));

        return neighbours;
    }

    public static String createName(int x, int y) {
        return String.format("%d_%d", x, y);
    }
}
