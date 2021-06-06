package com.visualiser.pathfinder.endpoint;

import com.visualiser.pathfinder.Entities.Board;
import com.visualiser.pathfinder.Entities.Coordinate;
import com.visualiser.pathfinder.algorithms.astar.RouteFinder;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
@Path("/algorithm/astar")
public class AStarService {
    @POST
    @Produces("application/json")
    public Response computeAStar(Board board) {
        Coordinate start = new Coordinate(board.getStart().getX(), board.getStart().getY());
        Coordinate end = new Coordinate(board.getEnd().getX(), board.getEnd().getY());

        RouteFinder routeFinder = new RouteFinder(board.getSize(), board.getWalls());

        try {
            List<Coordinate> result = routeFinder.findRoute(start, end);
            return Response.status(200).entity(result).build();
        } catch(IllegalStateException e) {
            System.out.println("Error occurred whilst trying to compute route with A* " + e);

            return Response.status(500).build();
        }
    }
}