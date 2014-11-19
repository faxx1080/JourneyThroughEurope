/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game.event;

import java.util.Observable;

/**
 * Cheap trick to force notifyObservers to always fire.
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class ObservableAlways extends Observable {

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg); 
    }
    
}
