package com.visualiser.pathfinder.interfaces;

public interface Scorer<T extends GraphNode> {
    int computeCost(T from, T to);
}
