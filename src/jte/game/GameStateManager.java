/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import jte.game.event.ObservableAlways;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jte.file.CityLoader;
import properties_manager.PropertiesManager;
import static jte.JTEPropertyType.*;
import jte.file.CityRouteLoader;
import jte.ui.ErrorHandler;
import jte.ui.EventHandlerMain;
import jte.ui.JourneyUI;

/**
 *
 * @author Frank
 */
public class GameStateManager {

    public int NUM_CARDS;
    public double CITY_RADIUS;
    public int MAGIC_ROLL_AGAIN = 6;
    
    private GameState gameState = GameState.NOT_STARTED;
    
    private static final PropertiesManager props = PropertiesManager.getPropertiesManager();

    private Dice dice;
    
    private int movesLeft;
    private boolean rolledSix = false;

    // zero based
    private int currentPlayer;

    private List<Player> players;
    
    // As per HW2 + HW3, this type of coupling would be presumed to be ok.
    private JourneyUI ui;
    
    // Player Num, and Details about move.
    // Observables
//    private ObservableAlways playerInitialized; //For putting player on map
//    private ObservableAlways playerCardsDrawn;
//    private ObservableAlways playerMoved;
//    private ObservableAlways playerClearCard;
//    private ObservableAlways gameOver;
    //End obs
    
    // on city select, run onCitySelect -> playerMove* -> playerClearCard* -> gameOver*
    
    
    // Store because instructions say so.
    private City nextRedCard;
    
    private Map<Integer, City> cityToID;
    private CityGraph cityNeigh;
    private JTELog logger;
    // private Mover mover;
    private String currentMessage;


    public GameStateManager(int numCards, double cityRadius, String[] playerNames, boolean[] playerIsCPU, JourneyUI ui) {
        this.ui = ui;
        NUM_CARDS = numCards;
        CITY_RADIUS = cityRadius;
        dice = new Dice();
        if (playerNames.length != playerIsCPU.length) {
            throw new IllegalArgumentException("# players != # of array entries in isCPU");
        }
        
        players = new ArrayList<>(playerNames.length);
        
        for (int i = 0; i < playerNames.length; i++) {
            Player t = new Player(playerNames[i], playerIsCPU[i]);
            players.add(t);
        }
        
        loadCitiesIntoGSM();
    }
    
    private void loadCitiesIntoGSM() {
        logger = new JTELog();
        String schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_CITIESSCH);
        CityLoader cityLoader = new CityLoader(schemaPath);

        try {
            cityToID = cityLoader.readCities(props.getProperty(DATA_PATH) + props.getProperty(XML_CITIESFILELOC));
        } catch (IOException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_RTSCH);
        String neighPath = props.getProperty(DATA_PATH) + props.getProperty(XML_RTFILELOC);
        
        try {
            cityNeigh = new CityRouteLoader(schemaPath, cityToID).readCityNeighbors(neighPath);
        } catch (IOException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Called by UI when ready.
     * Precondition: 
     * Postcondition: Player 0 ready.
     */
    public void initGameAndCards() throws IllegalStateException {
        cardLoading();
        
        currentPlayer = 0;
        // initGameplay(players.get(currentPlayer));
    }
    
    /*
    
    flow: initGameAndCards -> cardLoad -> done.
    startPlayer();
    
    */
    
    public void startPlayer() {
        // uses currplayer
        
        initGameplay(players.get(currentPlayer));
    }
    
    
    /**
     * Initializes and spawns player.
     * @param pl 
     */
    private void initGameplay(Player pl) {
        dice.roll();
        movesLeft = dice.getRoll();
        if (movesLeft == MAGIC_ROLL_AGAIN)
            rolledSix = true;
        uiDiceRolled(movesLeft);
        
    }
    
    private void startMove(Player pl) {
        
    }
    
    /**
     * Called by the UI when a player moves, passing in the city moved to.
     */
    public void movePlayer(City moveTo) {
        
    }
    
    // UI Methods
    // initPlayer(player)
    //
    
    private void uiInitPlayer(Player pl) {
        
    }
    
    private void uiDiceRolled(int diceRoll) {
        
    }
    
    private void uiAnimateCard(City city, Player pl) {
        
    }
    
    private void uiActivatePlayer(Player pl) {
        
    }
    
    private void uiMovePlayer(City toCity, Player pl) {
        
    }
    
    private void uiWinGame(Player pl) {
        
    }
     
    // UI Methods End
    
    
    /**
     * Postcondition: All players spawned and ready to run.
     */
    private void cardLoading() {
        List<Integer> cardsDealt = new ArrayList<>(cityToID.size());
        // build list out with 180 ints, 0-179
        
        for (int i = 0; i < cityToID.size(); i++) {
            cardsDealt.set(i, i);
        }
        
        Collections.shuffle(cardsDealt);
        
        for (Player p: players) {
            //player.notifyObservers(p);
            
            // Give cards red, yellow, green in order, as per # of cards.
            int cardsRemain = NUM_CARDS;
            while (cardsRemain >= 0) {
                
                // These can be done as red, yellow, green
                // Assumption: Shuffling will not put all x colored cards
                // at end. Therefore avg case: O(n) per run.
                
                int iterat = 0;
                int lastID;
                
                // make red
                do {
                    iterat = (iterat+1) % cityToID.size();
                    lastID = cardsDealt.get(iterat);
                } while (!cityToID.get(lastID).getColor().equals(Color.RED));
                p.addCard(cityToID.get(lastID));
                cardsDealt.remove(iterat);
                
                p.setCurrentCity(cityToID.get(lastID));
                p.setHomeCity(cityToID.get(lastID));
                p.setLastCity(cityToID.get(lastID));
                
                uiInitPlayer(p);
                uiAnimateCard(cityToID.get(lastID), p);
                
                
                //lastCityDrawn.notifyObservers(cityToID.get(lastID));
                cardsRemain--;
                
                if (cardsRemain < 0) break;
                
                // make yellow
                iterat = 0;
                do {
                    iterat = (iterat+1) % cityToID.size();
                    lastID = cardsDealt.get(iterat);
                } while (!cityToID.get(lastID).getColor().equals(Color.YELLOW));
                p.addCard(cityToID.get(lastID));
                cardsDealt.remove(iterat);
                
                uiAnimateCard(cityToID.get(lastID), p);
                cardsRemain--;
                
                if (cardsRemain < 0) break;
                
                // make green
                iterat = 0;
                do {
                    iterat = (iterat+1) % cityToID.size();
                    lastID = cardsDealt.get(iterat);
                } while (!cityToID.get(lastID).getColor().equals(Color.GREEN));
                p.addCard(cityToID.get(lastID));
                cardsDealt.remove(iterat);
                
                uiAnimateCard(cityToID.get(lastID), p);
                cardsRemain--;
                
                if (cardsRemain < 0) break;
            }
            
        }
        
        //buffer next red
        int iterat = 0; int lastID = -1;
        do {
            iterat = (iterat+1) % cityToID.size();
            lastID = cardsDealt.get(iterat);
        } while (!cityToID.get(lastID).getColor().equals(Color.RED));
        
        nextRedCard = cityToID.get(lastID);
        
    }
    
    
    /**
     * Gets the one-based player.
     * @param playerNumber
     * @return 
     */
    public String getPlayerName(int playerNumber) {
        if (playerNumber > players.size()+1 || playerNumber <= 0) {return "";}
        return players.get(playerNumber-1).getName();
    }
    
    /**
     * Returns the city given by the id, or null if non-existent.
     * @param id
     * @return 
     */
    public City getCityFromID(int id) {
        return cityToID.get(id);
    }
    
    /**
     * This method gets from the hashmap the city from the given coordinate,
     * then applies a circular range of radius CITY_RADIUS from the city and
     * checks if the given point is in that range.
     * @param coord
     * @param dist
     * Optional parameter that outputs the distance from the city. Uses
     * the zeroth position for output.
     * @return The city if found, or null.
     */
    public City getCityFromCoord(Point2D coord, double[] dist) {
        
        City closestCity;
        double shortestDist = Double.MAX_VALUE;
        double currentDist;
        int lastGoodID = -1; //Seintenial for fail.
        
        for (int id = 0; id < cityToID.size(); id++) {
            currentDist = coord.distance(cityToID.get(id).getCoord());
            if ((currentDist < shortestDist) && (currentDist < CITY_RADIUS)) {
                shortestDist = currentDist;
                lastGoodID = id;
            }
        }
        if (dist != null && dist.length >= 1) {dist[0] = shortestDist;}
        return cityToID.get(lastGoodID);
        
    }
    
    public JTELog getLog() {
        return logger;
    }
    
}
