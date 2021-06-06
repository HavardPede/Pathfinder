package com.visualiser.pathfinder.algorithms.astar;

import com.visualiser.pathfinder.Entities.Coordinate;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private final Set<Coordinate> nodes;
    private final Map<String, Set<String>> connections;

    public Graph(Coordinate boardSize) {
        this.nodes = Helper.generateNodes(boardSize);
        this.connections = Helper.generateConnections(boardSize);
    }

    public Coordinate getNode(String id) {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No node found with ID " + id));
    }

    public Set<Coordinate> getConnections(Coordinate node) {
        return connections.get(node.getId()).stream()
                .map(this::getNode)
                .collect(Collectors.toSet());
    }
}
