package com.visualiser.pathfinder.algorithms.astar;

import com.visualiser.pathfinder.Entities.Coordinate;
import java.util.*;

public class RouteFinder {
    private final Graph graph;
    private final NodeScorer scorer;
    private final Map<Integer, List<Integer>> walls;

    public RouteFinder(Coordinate boardSize, Coordinate[] walls) {
        this.graph = new Graph(boardSize);
        this.scorer = new NodeScorer();
        this.walls = createWallMap(walls);
    }

    public List<Coordinate> findRoute(Coordinate from, Coordinate to) {
        Map<Coordinate, RouteNode> allNodes = new HashMap<>();
        Queue<RouteNode> openSet = new PriorityQueue<>();

        RouteNode start = new RouteNode(from, null, 0d, scorer.computeCost(from, to));
        allNodes.put(from, start);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            RouteNode bestNode = openSet.poll();

            if (bestNode.getCurrent().equals(to)) {
                List<Coordinate> route = new ArrayList<>();
                RouteNode current = bestNode;

                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);

                return route;
            }

            graph.getConnections(bestNode.getCurrent()).forEach(connection -> {
                RouteNode potentialNode = allNodes.getOrDefault(connection, new RouteNode(connection));
                if (isWall(connection)) return;

                allNodes.put(connection, potentialNode);
                double routeScore = bestNode.getRouteScore() + scorer.computeCost(bestNode.getCurrent(), connection);

                if (routeScore < potentialNode.getRouteScore()) {
                    potentialNode.setPrevious(bestNode.getCurrent());
                    potentialNode.setRouteScore(routeScore);
                    potentialNode.setEstimatedScore(routeScore + scorer.computeCost(connection, to));
                    openSet.add(potentialNode);
                }
            });
        }

        throw new IllegalStateException("No route found");
    }

    private Map<Integer, List<Integer>> createWallMap(Coordinate[] walls) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (Coordinate wall : walls) {
            List<Integer> yList = map.getOrDefault(wall.getX(), new ArrayList<>());
            yList.add(wall.getY());
            map.put(wall.getX(), yList);
        }
        return map;
    }

    private boolean isWall(Coordinate coordinate) {
        List<Integer> yValues = this.walls.get(coordinate.getX());
        return yValues != null && this.walls.get(coordinate.getX()).contains(coordinate.getY());
    }
}
