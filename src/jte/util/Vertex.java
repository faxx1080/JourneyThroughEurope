/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import jte.game.City;

/**
 * @author Paul Fodor
 */
public class Vertex implements Comparable<Vertex> {

    public final City name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(City argName) {
        name = argName;
    }
    
    public City getValue() {
        return name;
    }

    public String toString() {
        return name.toString();
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
