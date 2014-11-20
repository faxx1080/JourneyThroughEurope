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
    private Restrictions cardOverries;

    public Player(String name, boolean isCPU) {
        this.name = name;
        this.isCPU = isCPU;
        citiesVisited = new ArrayList<>();
        cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isIsCPU() {
        return isCPU;
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

    public Restrictions getCardOverries() {
        return cardOverries;
    }

    public void setCardOverries(Restrictions cardOverries) {
        this.cardOverries = cardOverries;
    }
    
    
    
    
}
