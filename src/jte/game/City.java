/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import javafx.geometry.Point2D;

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
    private boolean isHarbor;
    private boolean isAirport;

    /**
     * Returns the sector of the flight map this city is in.
     */
    public int getFlightLoc() {
        return flightLoc;
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
     * Gets if this city is a harbor or not.
     * @return 
     */
    public boolean isHarbor() {
        return isHarbor;
    }

    /**
     * Gets if this city is an airport or not.
     * @return 
     */
    public boolean isAirport() {
        return isAirport;
    }

    /**
     * Creates a city with the specified info.
     */
    public City(int flightLoc,
            Point2D flightMapLoc,
            Point2D coord,
            int id,
            String desc,
            String name,
            boolean isHarbor,
            boolean isAirport) {
        this.flightLoc = flightLoc;
        this.flightMapLoc = flightMapLoc;
        this.coord = coord;
        this.id = id;
        this.desc = desc;
        this.name = name;
        this.isHarbor = isHarbor;
        this.isAirport = isAirport;
    }

    
    
}
