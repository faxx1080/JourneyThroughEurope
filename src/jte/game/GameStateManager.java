/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jte.util.UnweightedGraph;

/**
 *
 * @author Frank
 */
public class GameStateManager {
    public final int NUM_CARDS;
    public final int CITY_RADIUS;
    
    private Dice dice;
    private int movesLeft;
    
    // zero based
    private int currentPlayer;
    
    private List<Player> players;
    private List<Card> cardPileRed;
    private List<Card> cardPileYellow;
    private List<Card> cardPileGreen;
    
    private UnweightedGraph<City> cityGraph;
    private HashMap<Integer, City> cityToID;
    private JTELog logger;
    // private Mover mover;
    private String currentMessage;
    
    
    public GameStateManager(int numCards, int cityRadius, String[] playerNames, boolean[] playerIsCPU) {
        NUM_CARDS = numCards;
        CITY_RADIUS = cityRadius;
        
        // Not part of HW5
//        if (playerNames.length != playerIsCPU.length) {
//            throw new IllegalArgumentException("# players != # of array entries in isCPU");
//        }
//        
//        players = new ArrayList<>(playerNames.length);
//        
//        for (int i = 0; i < playerNames.length; i++) {
//            Player t = new Player();
//            
//        }
        
        
        
    }
    
}
