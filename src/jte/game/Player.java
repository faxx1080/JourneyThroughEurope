/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.List;

/**
 * This class represents a player.
 * @author Frank
 */
public class Player {
    private String name;
    private boolean isCPU;
    private City homeCity;
    private City currentCity;
    private City lastCity;
    private List<City> citiesVisited;
    private List<Card> cards;
    private Restrictions cardOverries;

    public Player(String name, boolean isCPU) {
        this.name = name;
        this.isCPU = isCPU;
    }

    public String getName() {
        return name;
    }

    public boolean isIsCPU() {
        return isCPU;
    }
    
    
    
}
