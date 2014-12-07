/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {

    /**
     * Inits shortest path.
     * @param source Start point.
     * @param maxWeight Do not evaluate paths greater than maxWeight
     */
    public static void computePaths(Vertex source, int maxWeight, List<Vertex> remove) {
        System.out.println("");
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();
            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    if (remove.contains(e.target)) {continue;}
                    if ((e.weight != 6) && (e.weight > maxWeight)) continue; // chopping block
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }
    
    /**
     * Inits shortest path.
     * @param source Start point.
     * @param maxWeight Do not evaluate paths greater than maxWeight
     */
    public static void computePaths(Vertex source, int maxWeight) {
        computePaths(source, maxWeight, new ArrayList<>());
    }

    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }
/*
    public static void main(String[] args) {
        Vertex v0 = new Vertex("London");
        Vertex v1 = new Vertex("Dover");
        Vertex v2 = new Vertex("Calais");
        Vertex v3 = new Vertex("Paris");
        Vertex v4 = new Vertex("Rotterdam");
        Vertex v5 = new Vertex("Brussels");
        Vertex v6 = new Vertex("Lille");
        v0.adjacencies = new Edge[]{new Edge(v1, 1), new Edge(v4, 6), new Edge(v3, 2)};
        v1.adjacencies = new Edge[]{new Edge(v0, 1), new Edge(v2, 1)};
        v2.adjacencies = new Edge[]{new Edge(v1, 1), new Edge(v3, 1), new Edge(v6, 1)};
        v3.adjacencies = new Edge[]{new Edge(v0, 2), new Edge(v2, 1), new Edge(v6, 1)};
        v4.adjacencies = new Edge[]{new Edge(v0, 6), new Edge(v5, 1)};
        v5.adjacencies = new Edge[]{new Edge(v4, 1), new Edge(v6, 1)};
        v6.adjacencies = new Edge[]{new Edge(v3, 1), new Edge(v5, 1)};
        Vertex[] vertices = {v0, v1, v2, v3, v4, v5, v6};
// Paths from London
        computePaths(v0);
        for (Vertex v : vertices) {
            System.out.println("Distance from London to " + v + ": "
                    + v.minDistance);
            List<Vertex> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }*/
}

/*
Output:
Distance from London to London: 0.0
Path: [London]
Distance from London to Dover: 1.0
Path: [London, Dover]
Distance from London to Calais: 2.0
Path: [London, Dover, Calais]
Distance from London to Paris: 2.0
Path: [London, Paris]
Distance from London to Rotterdam: 5.0
Path: [London, Paris, Lille, Brussels, Rotterdam]
Distance from London to Brussels: 4.0
Path: [London, Paris, Lille, Brussels]
Distance from London to Lille: 3.0
Path: [London, Paris, Lille]*/
