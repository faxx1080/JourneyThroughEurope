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
import jte.JTEResourceType;
import jte.file.CityRouteLoader;
import jte.ui.ErrorHandler;
import jte.ui.EventHandlerMain;
import jte.ui.JourneyUI;
import jte.util.RLoad;

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
        if (gameState != GameState.NOT_STARTED) throw new IllegalStateException("Game already started.");
        cardLoading();
        currentPlayer = 0;
        //uiActivatePlayer(players.get(0));
        gameState = GameState.READY_ROLL;
        initGameplay(players.get(currentPlayer));
    }
    
    /*
    
    flow: initGameAndCards -> cardLoad -> done.
    startPlayer();
    
    */
    
    /**
     * Called by ui when ready to initialize a players turn.
     * @throws IllegalStateException 
     */
    public void startPlayer() throws IllegalStateException {
        // uses currplayer
        if (gameState != GameState.READY_ROLL) throw new
                IllegalStateException("Player still has moves left.");
        initGameplay(players.get(currentPlayer));
    }
    
    public int getMovesLeft() {
        return movesLeft;
    }
    
    
    /**
     * Rolls dice for player.
     * @param pl 
     */
    private void initGameplay(Player pl) {
        dice.roll();
        movesLeft = dice.getRoll();
        if (movesLeft == MAGIC_ROLL_AGAIN)
            rolledSix = true;
        uiDiceRolled(movesLeft);
        gameState = GameState.INPUT_MOVE;
        uiActivatePlayer(pl);
        // In UI: Draw lines.
    }
    
    
    /**
     * Called by the UI when a player moves, passing in the city moved to.
     */
    public void movePlayer(City moveTo) {
        if (!(gameState == GameState.INPUT_MOVE)) {
            // Fail.
            return;
        }
        movePlayerInternal(moveTo);
        if ((movesLeft == 0) && (rolledSix)) {
            gameState = GameState.READY_ROLL;
        } else if (movesLeft == 0) {
            currentPlayer = (currentPlayer + 1) % players.size();
            
        }
        uiActivatePlayer(getCurrentPlayer());
        
    }
    
    private void movePlayerInternal(City moveTo) {
        City currLoc = players.get(currentPlayer).getCurrentCity();
        Player cp = players.get(currentPlayer);
        // TODO Check card instructions
        List<City> nearby = getCityNeigh(currLoc);
        boolean noRemove = false;
        if (nearby.contains(moveTo)) {
            //City moveFrom = getCurrentPlayer().getCurrentCity();
            players.get(currentPlayer).setCurrentCity(moveTo);
            getCurrentPlayer().getCitiesVisited().add(moveTo);
            uiMovePlayer(currLoc, moveTo, players.get(currentPlayer));
            if (checkPlayerStats()) return;
            movesLeft--;
        } else if (getCityNeighSea(currLoc).contains(moveTo)) {
            if (!(movesLeft == dice.getRoll())) {
                // Fail.
                currentMessage = RLoad.getString(JTEResourceType.STR_NOSWIM);
                return;
            }
            //City moveFrom = getCurrentPlayer().getCurrentCity();
            players.get(currentPlayer).setCurrentCity(moveTo);
            getCurrentPlayer().getCitiesVisited().add(moveTo);
            uiMovePlayer(currLoc, moveTo, players.get(currentPlayer));
            if (checkPlayerStats()) return;
            movesLeft = 0;
        }
        
    }    
    
    /**
     * Updates player card info on move.
     * If player landed at a card, ret true; else false
     * @return 
     */
    private boolean checkPlayerStats() {
        Player cp = getCurrentPlayer();
        int index = getCurrentPlayer().getCards().indexOf(getCurrentPlayer().getCurrentCity());
        if (index > 0) {
            // remove only if not last city
            if (cp.getCards().size() > 1) {
                // cannot remove first card
                if (cp.getHomeCity().equals(cp.getCurrentCity())) {
                    return false;
                }
            }
            City cardRemove = getCurrentPlayer().getCards().get(index);
            getCurrentPlayer().getCards().remove(index);
            uiAnimateCardOut(cardRemove, getCurrentPlayer());
            return false;
        }
        if (index < 0) {
            if (getCurrentPlayer().getCards().isEmpty() && getCurrentPlayer().getCurrentCity().equals(getCurrentPlayer().getHomeCity())) {
                gameState = GameState.GAME_OVER;
                uiWinGame(getCurrentPlayer());
                return true;
            }
        }
        return false;
    }
    
    // UI Methods
    // initPlayer(player)
    // Needs to draw player on map.
    
    private void uiInitPlayer(Player pl) {
        ui.uiInitPlayer(pl);
    }
    
    private void uiDiceRolled(int diceRoll) {
        ui.setDice(diceRoll);
    }
    
    private void uiAnimateCard(City city, Player pl) {
        ui.addCard(getPlayerNum(pl), city);
    }
    
    private void uiAnimateCardOut(City city, Player pl) {
        ui.removeCard(getPlayerNum(pl), city);
    }
    
    private void uiActivatePlayer(Player pl) {
        ui.activatePlayer(getPlayerNum(pl));
    }
    
    private void uiMovePlayer(City fromCity, City toCity, Player pl) {
        ui.movePlayerUI(toCity, fromCity);
    }
    
    private void uiWinGame(Player pl) {
        
    }
     
    // UI Methods End
    
    
    /**
     * Postcondition: All players spawned and ready to run.
     */
    private void cardLoading() {
        int size = cityToID.size();
        List<Integer> cardsDealt = new ArrayList<>(size);
        // build list out with 180 ints, 0-179
        
        for (int i = 0; i < cityToID.size(); i++) {
            cardsDealt.add(i);
        }
        
        Collections.shuffle(cardsDealt);
        
        // rgy gyr yrg rgy
        // Assumption: Shuffling will not put all x colored cards
        // at end. Therefore avg case: O(n) per run.

        int iterat = 0;
        int lastID;

        String red = RLoad.getString(JTEResourceType.STR_RED);
        String green= RLoad.getString(JTEResourceType.STR_GRE);
        String yellow=RLoad.getString(JTEResourceType.STR_YEL);

        HashMap<Integer, String> cardColor = new HashMap<>(3);
        cardColor.put(0, red);
        cardColor.put(1, green);
        cardColor.put(2, yellow);    
        boolean firstRun = false;
        int offset = 0;
        
        for (int j = NUM_CARDS; j > 0; j--) {
            
            for (int i = 0; i < players.size(); i++) {
                
                iterat = 0;  lastID = -1;
                
                Player p = players.get(i);

                do {
                    iterat = (iterat+1) % cityToID.size();
                    lastID = cardsDealt.get(iterat);
                } while (!cityToID.get(lastID).getColor().equals(cardColor.get((i+offset) % 3)));
                players.get(i).addCard(cityToID.get(lastID));

                cardsDealt.remove(iterat);
                
                if (!firstRun) {
                    p.setCurrentCity(cityToID.get(lastID));
                    p.setHomeCity(cityToID.get(lastID));
                    p.getCitiesVisited().add(cityToID.get(lastID));
                    uiInitPlayer(p);
                }
                
                
                uiAnimateCard(cityToID.get(lastID), p);
            }
            
            firstRun = true;
            offset++;
        }
        
        
        //buffer next red
        iterat = 0;  lastID = -1;
        do {
            iterat = (iterat+1) % cityToID.size();
            lastID = cardsDealt.get(iterat);
        } while (!cityToID.get(lastID).getColor().equals(red));
        
        nextRedCard = cityToID.get(lastID);
        
    }
    
    
    /**
     * Gets the one-based player.
     * @param playerNumber
     * @return 
     */
    public String getPlayerName(int playerNumber) {
        if (playerNumber > players.size() || playerNumber <= 0) {return "";}
        return players.get(playerNumber-1).getName();
    }
    
    /**
     * Zero based
     * @param pl
     * @return
     * @throws IndexOutOfBoundsException 
     */
    public int getPlayerNum(Player pl) throws IndexOutOfBoundsException {
        return players.indexOf(pl);
    }
    
    /**
     * Zero based.
     * @param pl
     * @return 
     */
    public Player getPlayerObject(int pl) {
        return players.get(pl);
    }
    
    /**
     * Returns the city given by the id, or null if non-existent.
     * @param id
     * @return 
     */
    public City getCityFromID(int id) {
        return cityToID.get(id);
    }
    
    public List<City> getCityNeigh(City city) {
        return cityNeigh.getNeighbors(city.getId());
    }
    
    public List<City> getCityNeighSea(City city) {
        return cityNeigh.getNeighborsSea(city.getId());
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
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }
    
    public int getNumPlayers() {
        return players.size();
    }
    
}
