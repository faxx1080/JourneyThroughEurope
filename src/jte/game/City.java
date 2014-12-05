/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * This represents the data for a city.
 * @author Frank
 */
public class City {
    private int flightLoc;
    private Point2D flightMapLoc;
    private Point2D coord;
    private int id;
    private String desc;
    private String name;
    private boolean isAirport;
    private String cardColor;
    private boolean hasInst;
    private Restriction restr;
    // private CardInstruction instType;

    /**
     * Returns the sector of the flight map this city is in.
     */
    public int getFlightLoc() {
        return flightLoc;
    }

    public Restriction getRestriction() {
        return restr;
    }
    
    public boolean hasInst() {
        return (restr.getType() != InstructionTypes.NOTHING);
    }
    
    /**
     * Returns the coordinate in the flight map this city is at.
     * @return 
     */
    public Point2D getFlightMapLoc() {
        return flightMapLoc;
    }

    /**
     * Gets the pixel coordinate this city is at on the game board.
     */
    public Point2D getCoord() {
        return coord;
    }

    /**
     * Gets the id of this city.
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the description of this city.
     * @return 
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Gets the name of this city.
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the color of this city's card.
     */
    public String getColor() {
        return cardColor;
    }
    
    /**
     * Gets if this city is an airport or not.
     * @return 
     */
    public boolean isAirport() {
        return isAirport;
    }
    
    public City(int flightLoc,
            Point2D flightMapLoc,
            Point2D coord,
            int id,
            String desc,
            String name,
            boolean isAirport,
            String col,
            int flightQ,
            Point2D flCoord,
            int itype,
            int iv1, int iv2, int ic1, int ic2) {
        this.flightLoc = flightLoc;
        this.flightMapLoc = flightMapLoc;
        this.coord = coord;
        this.id = id;
        this.desc = desc;
        this.name = name;
        this.isAirport = isAirport;
        this.cardColor = col;
        this.flightLoc = flightQ;
        this.flightMapLoc = flCoord;
        this.hasInst = false;
        Restriction t = new Restriction(InstructionTypes.NOTHING, 0,0,0,0);
        if (itype > 0) {
            this.hasInst = true;
            t = new Restriction(InstructionTypes.intToType(itype), iv1, iv2, ic1, ic2);
        }
        restr = t;
    }
    
    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
    
}
