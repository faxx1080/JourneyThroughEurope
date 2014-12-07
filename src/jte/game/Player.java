/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.ArrayList;
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
    private List<City> citiesVisited;
    private List<City> cards;
    private List<Integer> rollHistory;
    private Restriction activeRestriction;

    public Player(String name, boolean isCPU) {
        this.name = name;
        this.isCPU = isCPU;
        citiesVisited = new ArrayList<>();
        cards = new ArrayList<>();
        this.activeRestriction = new Restriction(InstructionTypes.NOTHING, 0, 0, 0, 0);
        this.rollHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public List<Integer> getRollHistory() {
        return rollHistory;
    }
    
    public void setRollHistory(List<Integer> rollH) {
        rollHistory = rollH;
    }

    public boolean isIsCPU() {
        return isCPU;
    }
    
    public City getLastCity() {
        if (citiesVisited.size() < 2) return null;
        return citiesVisited.get( citiesVisited.size() - 2);
    }
    
    public void addCard(City cd) {
        cards.add(cd);
    }

    public City getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(City homeCity) {
        this.homeCity = homeCity;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public List<City> getCards() {
        return cards;
    }

    public List<City> getCitiesVisited() {
        return citiesVisited;
    }

    public Restriction getActiveRestriction() {
        return activeRestriction;
    }

    public void setActiveRestriction(Restriction cardOverries) {
        this.activeRestriction = cardOverries;
    }
    
    
    
    
}
