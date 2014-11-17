/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.List;
import java.util.Map;
import jte.util.AbstractSimpleGraph;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class CityGraph extends AbstractSimpleGraph<Integer, City> {

    public CityGraph(Map<Integer, List<City>> neighbors, Map<Integer, List<City>> neighborsSea) {
        super.neighbors = neighbors;
        super.neighborsSea = neighborsSea;
    }
    
}
