/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class CardDeal {
    
    private City card;
    private String playerName;
    
    public CardDeal(City card, String playerName) {
        this.card = card;
        this.playerName = playerName;
    }
    
    public City getCard() {
        return card;
    }
    
    public String getPlayerName() {
        return playerName;
    }
}
