/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import jte.Constants;
import jte.JTEPropertyType;
import jte.file.CityLoader;
import properties_manager.PropertiesManager;
import static jte.JTEPropertyType.*;
import jte.JTEResourceType;
import jte.file.CityRouteLoader;
import jte.file.FlightLoader;
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
    
    private JourneyUI ui;
    
    private boolean fliedAlready;
    
    private City nextRedCard;
    
    private final int NUM_FLIGHT_ZONES;
        
    private Map<Integer, City> cityToID;
    private Map<Integer, List<Integer>> flQuadToQuads;
    private Map<Integer, List<City>> flQuadToCities;
    
    private CityGraph cityNeigh;
    private JTELog logger;
    // private Mover mover;
    private String currentMessage;
    
    public GameStateManager(int numCards, double cityRadius, String[] playerNames, boolean[] playerIsCPU, JourneyUI ui) {
        this.ui = ui;
        NUM_CARDS = numCards;
        CITY_RADIUS = cityRadius;
        NUM_FLIGHT_ZONES = Integer.parseInt(props.getProperty(JTEPropertyType.NUM_FLIGHT_ZONES));
        dice = new Dice();
        if (playerNames.length != playerIsCPU.length) {
            throw new IllegalArgumentException("# players != # of array entries in isCPU");
        }
        
        players = new ArrayList<>(playerNames.length);
        
        for (int i = 0; i < playerNames.length; i++) {
            Player t = new Player(playerNames[i], playerIsCPU[i]);
            players.add(t);
        }
        flQuadToCities = new HashMap<>();
        
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
        
        for(int i = 0; i<=NUM_FLIGHT_ZONES; i++) {
            flQuadToCities.put( i , new ArrayList<>(180));
        }
        
        cityToID.values().stream().forEach((c) -> {
            flQuadToCities.get(c.getFlightLoc()).add(c);
        });
        
        schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_RTSCH);
        String neighPath = props.getProperty(DATA_PATH) + props.getProperty(XML_RTFILELOC);
        
        try {
            cityNeigh = new CityRouteLoader(schemaPath, cityToID).readCityNeighbors(neighPath);
        } catch (IOException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_FLSCH);
        neighPath = props.getProperty(DATA_PATH) + props.getProperty(XML_FLFILELOC);
        
        try {
            flQuadToQuads = new FlightLoader(schemaPath).readAirportNeighbors(neighPath);
        } catch (IOException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//    public boolean isCityOccupied(City city) {
//        return (checkIfOccupiedP(city) != null);
//    }
//    
//    public Player checkIfOccupiedP(City city) {
//        Player out = null;
//        for (Player p: players) {
//            if (p.getCurrentCity().equals(city))
//                out = p;
//        }
//        return out;
//    }
    
    // End flight
    
    protected boolean getSixFlag() {
        return rolledSix;
    }
    
    protected void setSixFlag(boolean b) {
        rolledSix = b;
    }
    
    public City getRedCard() {
        return nextRedCard;
    }
    
    /**
     * Called by UI when ready.
     * Precondition: 
     * Postcondition: Player 0 ready.
     */
    public void initGameAndCards() throws IllegalStateException {
        if (gameState != GameState.NOT_STARTED) throw new IllegalStateException("Game already started.");
        cardLoading();
        
        // Animate!
        ui.animateCards();
    }
    
    public void continueInit() {
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
    
    protected void setMovesLeft(int m) {
        movesLeft = m;
        uiMoveLeftUpdate(m);
    }
    
    /**
     * Rolls dice for player.
     * @param pl 
     */
    private void initGameplay(Player pl) {
        
        // Run some instructions here
        fliedAlready = false;
        dice.roll();
        if (pl.getCurrentCity().getId() == 161) {
            dice.roll(true); //Tirane forces fly-out.
        }
        movesLeft = dice.getRoll();
        if (movesLeft == MAGIC_ROLL_AGAIN)
            rolledSix = true;
        uiDiceRolled(movesLeft);
        gameState = GameState.INPUT_MOVE;
        uiActivatePlayer(pl, true);
    }
    
    // Null if none
    public List<City> get4FlyCities(City c) {
        if (!c.isAirport()) return null;
        List<City> out = new ArrayList<>();
        List<Integer> quads = flQuadToQuads.get(c.getFlightLoc());
        quads.stream().forEach((q) -> {
            out.addAll(flQuadToCities.get(q));
        });
        return out;
    }
    
    public boolean canFly() {
        return getCurrentPlayer().getCurrentCity().isAirport() && movesLeft > 1 && !fliedAlready;
    }
    
    // Null if none.
    public List<City> get2FlyCities(City c) {
        if (!c.isAirport()) return null;
        List<City> out = new ArrayList<>();
        out.add(c);
        out.addAll( flQuadToCities.get(c.getFlightLoc()) );
        return out;
    }
    
    public boolean movePlayer(City moveTo) {
        return movePlayer(moveTo, false);
    }
    
    /**
     * Called by the UI when a player moves, passing in the city moved to.
     */
    public boolean movePlayer(City moveTo, boolean fly) {
        //movesLeft = 2;
        if (!(gameState == GameState.INPUT_MOVE)) {
            // Fail.
            return false;
        }
        boolean res = movePlayerInternal(moveTo, fly);
        if (!res) {
            uiActivatePlayer(getCurrentPlayer(), isFirstMove());
            return false;
        }
        
        
        logger.clear();
        StringBuilder log = new StringBuilder();
        
        // Netbeans said so!
        players.stream().map((p) -> {
            log.append(p.getName());
            log.append('\n');
            return p;
        }).forEach((p) -> {
            for (int i = 0; i < p.getCitiesVisited().size(); i++) {
                log.append((i+1));
                log.append(" ");
                log.append(p.getCitiesVisited().get(i));
                log.append('\n');
            }
        });
        
        logger.appendText(log.toString());
        
        if (gameState == GameState.GAME_OVER) {return true;}
        if ((movesLeft == 0) && (rolledSix)) {
            rolledSix = false;
            gameState = GameState.READY_ROLL;
            initGameplay(getCurrentPlayer());
        } else if (movesLeft == 0) {
            currentPlayer = (currentPlayer + 1) % players.size();
            initGameplay(getCurrentPlayer());
        } else {
        uiActivatePlayer(getCurrentPlayer(), isFirstMove()); }
        return true;
    }
    
    public boolean isFirstMove(){
        //return true;
        return dice.getRoll()==getMovesLeft();
    }
    
    public boolean isCityNoGood(City moveTo) {
        for (Player p: players) {
            if (p.equals(getCurrentPlayer())) continue;
            if (p.getCurrentCity().equals(moveTo) && movesLeft == 1) {
                currentMessage = RLoad.getString(JTEResourceType.STR_NOSQUISHING);
                return true;
            }
        }
        
        if (getCurrentPlayer().getLastCity().equals(moveTo) && getCurrentPlayer().getCurrentCity().equals(moveTo)) {
            currentMessage = RLoad.getString(JTEResourceType.STR_NOBACKSIES);
            return true;
        }
        return false;
    }
    
    private boolean movePlayerInternal(City moveTo, boolean fly) {
        City currLoc = players.get(currentPlayer).getCurrentCity();
        Player cp = players.get(currentPlayer);
        List<City> nearby = getCityNeigh(currLoc);
        if (isCityNoGood(moveTo)) return false;
        
        // Try to fly.
        if (fly) {
            // case 1: in same spot
            if (get2FlyCities(currLoc).contains(moveTo) && movesLeft >= 2) {
                fliedAlready = true;
                players.get(currentPlayer).setCurrentCity(moveTo);
                getCurrentPlayer().getCitiesVisited().add(moveTo);
                uiMovePlayer(currLoc, moveTo, players.get(currentPlayer));
                if (checkPlayerStats()) return true;
                movesLeft-= 2;
            }
            
            else if(get4FlyCities(currLoc).contains(moveTo) && movesLeft >= 4) {
                fliedAlready = true;
                players.get(currentPlayer).setCurrentCity(moveTo);
                getCurrentPlayer().getCitiesVisited().add(moveTo);
                uiMovePlayer(currLoc, moveTo, players.get(currentPlayer));
                if (checkPlayerStats()) return true;
                movesLeft-= 4;
            } else {return false;}
            
            if (moveTo.getId() == 161) {
                movesLeft = 0; // Forced move-over.
            }
            return true;
        }
        
        
        if (nearby.contains(moveTo)) {
            //City moveFrom = getCurrentPlayer().getCurrentCity();
            players.get(currentPlayer).setCurrentCity(moveTo);
            getCurrentPlayer().getCitiesVisited().add(moveTo);
            uiMovePlayer(currLoc, moveTo, players.get(currentPlayer));
            if (checkPlayerStats()) return true;
            movesLeft--; return true;
        } else if (getCityNeighSea(currLoc).contains(moveTo)) {
            if (!isFirstMove()) {
                // Fail.
                currentMessage = RLoad.getString(JTEResourceType.STR_NOSWIM);
                return false;
            }
            //City moveFrom = getCurrentPlayer().getCurrentCity();
            players.get(currentPlayer).setCurrentCity(moveTo);
            getCurrentPlayer().getCitiesVisited().add(moveTo);
            uiMovePlayer(currLoc, moveTo, players.get(currentPlayer));
            if (checkPlayerStats()) return true;
            movesLeft = 0; return true;
        }
        
        return false;
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
            if (getCurrentPlayer().getCards().get(index).getRestriction().getType() != InstructionTypes.NOTHING) {
                movesLeft = 0;
            }
            // getCurrentPlayer().setActiveRestriction(getCurrentPlayer().getCards().get(index).getRestriction());
            /*// Funky
            boolean result = getCurrentPlayer().getActiveRestriction().execute(this);
            if (result)
                getCurrentPlayer().setActiveRestriction(new Restriction(InstructionTypes.NOTHING, 0, 0, 0, 0));
            
            // End funky*/
            getCurrentPlayer().getCards().remove(index);
            uiAnimateCardOut(cardRemove, getCurrentPlayer(), index);
            return false;
        }
        if (index == 0) { // Last card!
            if (getCurrentPlayer().getCurrentCity().equals(getCurrentPlayer().getHomeCity()) && getCurrentPlayer().getCards().size() == 1) {
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
    
    private void uiAnimateCardOut(City city, Player pl, int originalInd) {
        ui.removeCard(getPlayerNum(pl), city, originalInd);
    }
    
    private void uiActivatePlayer(Player pl, boolean firstMove) {
        ui.activatePlayer(getPlayerNum(pl), firstMove);
    }
    
    private void uiMovePlayer(City fromCity, City toCity, Player pl) {
        ui.movePlayerUI(toCity, fromCity);
    }
    
    private void uiWinGame(Player pl) {
        ui.uiWinGame(pl);
    }
    
    private void uiMoveLeftUpdate(int m) {
        ui.setMovesLeft(m);
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
            if (i == 161) continue; //TODO Tirane has no exits!
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
                if (Constants.DEBUG && (i == 0)) {continue;} // Ignore pl 1 if testing,
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
        
        if (Constants.DEBUG) {
            Player p = new Player("P1Cheat", false);
            players.set(0, p);
            p.addCard(cityToID.get(0));
            p.setCurrentCity(cityToID.get(0));
            p.setHomeCity(cityToID.get(0));
            p.getCitiesVisited().add(cityToID.get(0));
            uiInitPlayer(p);
            uiAnimateCard(cityToID.get(0), p);
            p.addCard(cityToID.get(80));
            uiAnimateCard(cityToID.get(80), p);
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
    public City getCityFromCoord(Point2D coord, double[] dist, boolean flightPl) {
        
        City closestCity;
        double shortestDist = Double.MAX_VALUE;
        double currentDist;
        int lastGoodID = -1; //Seintenial for fail.
        
        for (int id = 0; id < cityToID.size(); id++) {
            currentDist = coord.distance(cityToID.get(id).getCoord());
            if (flightPl)
                currentDist = coord.distance(cityToID.get(id).getFlightMapLoc());
            if ((currentDist < shortestDist) && (currentDist < CITY_RADIUS)) {
                shortestDist = currentDist;
                lastGoodID = id;
            }
        }
        if (dist != null && dist.length >= 1) {dist[0] = shortestDist;}
        return cityToID.get(lastGoodID);
        
    }
    
    public City getCityFromCoord(Point2D coord, double[] dist) {
        return getCityFromCoord(coord, dist, false);
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
