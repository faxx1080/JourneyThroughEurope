/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

/**
 * @author Paul Fodor
 */
public class Vertex<T> implements Comparable<Vertex<T>> {

    public final T name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(T argName) {
        name = argName;
    }
    
    public T getValue() {
        return name;
    }

    public String toString() {
        return name.toString();
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
