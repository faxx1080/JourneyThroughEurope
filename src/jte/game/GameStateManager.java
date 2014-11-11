/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import jte.file.CityLoader;
import jte.util.UnweightedGraph;
import properties_manager.PropertiesManager;
import static jte.JTEPropertyType.*;
import jte.ui.ErrorHandler;
import jte.ui.EventHandlerMain;

/**
 *
 * @author Frank
 */
public class GameStateManager {

    public int NUM_CARDS;
    public double CITY_RADIUS;
    private static final PropertiesManager props = PropertiesManager.getPropertiesManager();

    private Dice dice;
    private int movesLeft;

    // zero based
    private int currentPlayer;

    private List<Player> players;
    private List<Card> cardPileRed;
    private List<Card> cardPileYellow;
    private List<Card> cardPileGreen;

    private UnweightedGraph<City> cityGraph;
    private Map<Integer, City> cityToID;
    private JTELog logger;
    // private Mover mover;
    private String currentMessage;

    private EventHandlerMain eventhdr;
    private ErrorHandler errhdr;

    public GameStateManager(int numCards, double cityRadius, String[] playerNames, boolean[] playerIsCPU) {

        NUM_CARDS = numCards;
        CITY_RADIUS = cityRadius;

        if (playerNames.length != playerIsCPU.length) {
            throw new IllegalArgumentException("# players != # of array entries in isCPU");
        }
        
        players = new ArrayList<>(playerNames.length);
        
        for (int i = 0; i < playerNames.length; i++) {
            Player t = new Player(playerNames[i], playerIsCPU[i]);
            players.add(t);
        }
        logger = new JTELog();
        String schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_CITIESSCH);
        CityLoader cityLoader = new CityLoader(schemaPath);

        try {
            cityToID = cityLoader.readCities(props.getProperty(DATA_PATH) + props.getProperty(XML_CITIESFILELOC));
        } catch (IOException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        }

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
