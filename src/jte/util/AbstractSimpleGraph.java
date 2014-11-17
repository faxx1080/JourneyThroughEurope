/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public abstract class AbstractSimpleGraph<K,V> implements SimpleGraph<K,V> {
    
    protected Map<K, List<V>> neighbors;
    protected Map<K, List<V>> neighborsSea;
    
    // Shuts up warnings
    public AbstractSimpleGraph() {
        neighbors = null;
        neighborsSea = null;
    }
    
    @Override
    public List<V> getNeighbors(K element) {
        return neighbors.get(element);
    }
    
    @Override
    public List<V> getNeighborsSea(K element) {
        return neighborsSea.get(element);
    }
}
