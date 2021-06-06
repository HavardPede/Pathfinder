package com.visualiser.pathfinder.algorithms.astar;

import com.visualiser.pathfinder.Entities.Coordinate;

public class RouteNode implements Comparable<RouteNode> {
    private final Coordinate current;
    private Coordinate previous;
    private double routeScore;
    private double estimatedScore;

    public RouteNode(Coordinate current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    RouteNode(Coordinate current, Coordinate previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public Coordinate getCurrent() {
        return current;
    }

    public Coordinate getPrevious() {
        return previous;
    }

    public void setPrevious(Coordinate previous) {
        this.previous = previous;
    }

    public double getRouteScore() {
        return routeScore;
    }

    public void setRouteScore(double routeScore) {
        this.routeScore = routeScore;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }

    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(RouteNode other) {
        if (this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if (this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
    }
}
