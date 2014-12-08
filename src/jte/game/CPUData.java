/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jte.file.FlightLoader;
import jte.util.Dijkstra;
import jte.util.Edge;
import jte.util.Vertex;

/**
 *
 * @author Frank
 */
public class CPUData {
    List<Vertex> cities;

    public CPUData(Map<Integer, City> cityToID, CityGraph cityNeigh, Map<Integer, List<Integer>> flQuadToQuads, Map<Integer, List<City>> flQuadToCities) {
        
        cities = new ArrayList<>(cityToID.size());
        
        for (int i = 0; i < 180; i++) {
            City x = cityToID.get(i);
            cities.add(new Vertex(x));
        }
        
        for (int i = 0; i < 180; i++) {
            ArrayList<Edge> ed = new ArrayList<>();
            for (City k : cityNeigh.getNeighbors(i)) {
                ed.add(new Edge(cities.get(k.getId()), 1));
            }
            for (City k : cityNeigh.getNeighborsSea(i)) {
                ed.add(new Edge(cities.get(k.getId()), 6));
            }
            if (cities.get(i).getValue().isAirport()) {
                // add edge per city nearby.
                
                List<City> x = new ArrayList<>();
                List<Integer> quads = flQuadToQuads.get(cities.get(i).getValue().getFlightLoc());
                
                for (Integer q: quads) {
                    x.addAll(flQuadToCities.get(q));
                }
                
                for (City k: x) {
                   ed.add(new Edge(cities.get(k.getId()), 4));
                }
                
                x = flQuadToCities.get(cities.get(i).getValue().getFlightLoc());
                for (City k: x) {
                   ed.add(new Edge(cities.get(k.getId()), 2));
                }
                
               
            }
            Edge[] x = new Edge[0];
            cities.get(i).adjacencies = ed.toArray(x);
        }
        
        // Done building vertexes.
        
    }
    
    
    /**
     * If flight options: [1,2) use only land and sea.
     * If flight options: [2,4) use 2 flight + previous.
     * If flight options: [4,inf) use 4 flight + previous.
     * @param fromCity
     * @param toCity
     * @param flightOptions
     * @param removeCity: remove these cities from calculation.
     * @return 
     */
    public List<City> getShortestPathTo(City fromCity, City toCity, int flightOptions, List<City> removeCity) {
        int from, to;
        
        from = fromCity.getId();
        to = toCity.getId();
        if (from == to) {return new ArrayList<>();}
        if (flightOptions < 2) {flightOptions = 1;}
        if (flightOptions >= 2) {flightOptions = 2;}
        if (flightOptions >= 4) {flightOptions = 4;}
        
        List<Vertex> removee = new ArrayList<>();
        if (removeCity != null) {
            removeCity.stream().forEach((c) -> {
                removee.add(cities.get(c.getId()));
            });
        }
        
        Dijkstra.computePaths(cities.get(from), flightOptions, removee);
        
        List<Vertex> outv = Dijkstra.getShortestPathTo(cities.get(to));
        
        cities.stream().map((v) -> {
            v.previous = null;
            return v;
        }).forEach((v) -> {
            v.minDistance = Double.POSITIVE_INFINITY;
        });
        
        List<City> out = new ArrayList<>(outv.size());
        outv.stream().forEach((v) -> {
            out.add(v.getValue());
        });
        return out;
    }
    
    

}
