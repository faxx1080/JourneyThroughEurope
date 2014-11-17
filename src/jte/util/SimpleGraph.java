/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import java.util.List;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public interface SimpleGraph<K,V> {
    
    public List<V> getNeighbors(K element);
    
    public List<V> getNeighborsSea(K element);
    
}
