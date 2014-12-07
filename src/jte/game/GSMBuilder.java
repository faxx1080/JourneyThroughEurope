/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Frank
 */
public class GSMBuilder {
    int lastRoll;
    List<Player> player;
    String currMessage;
    int currPl;
    int movesLeft;
    String log;
    int nextRedCard;
    Map<Integer, City> cityToID;

    public GSMBuilder(int lastRoll, List<Player> player, String currMessage, int currPl,
            int movesLeft, String log, int nextRedCard, Map<Integer, City> cityToID) {
        this.lastRoll = lastRoll;
        this.player = player;
        this.currMessage = currMessage;
        this.currPl = currPl;
        this.movesLeft = movesLeft;
        this.log = log;
        this.nextRedCard = nextRedCard;
        this.cityToID = cityToID;
    }
    
    
    
}
