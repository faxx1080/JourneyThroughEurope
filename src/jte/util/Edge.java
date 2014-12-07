/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

public class Edge {

    public final Vertex target;
    public final int weight;

    public Edge(Vertex argTarget, int argWeight) {
        target = argTarget;
        weight = argWeight;
    }
    
    public String toString() {
        return target.toString() + ", " + weight;
    }
}