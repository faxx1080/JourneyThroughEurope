/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

/**
 * This holds the details about a JTE card.
 * @author Frank
 */
public class Card {
    private City backingCity;
    private String cityName;
    
    private int backingCityID;
    
    private Color cardColor;
    private CardInstruction instType;

    
//    /**
//     * Creates a city with the specified details.
//     * @param backingCity
//     * @param cardColor
//     * @param instType 
//     */
//    public Card(City backingCity, Color cardColor, InstructionType instType) {
//        this.backingCity = backingCity;
//        this.cardColor = cardColor;
//        this.instType = instType;
//    }
    
    /**
     * Creates a city with the specified details.
     * @param backingCityID
     * @param cardColor
     * @param instType 
     */
    public Card(int backingCityID, Color cardColor, CardInstruction instType) {
        this.backingCityID = backingCityID;
        this.cardColor = cardColor;
        this.instType = instType;
    }
    
    /**
     * Returns the city that this card represents
     * @return 
     */
    public int getBackingCityID() {
        return backingCityID;
    }

    public Color getCardColor() {
        return cardColor;
    }

    public CardInstruction getInstType() {
        return instType;
    }
    
    
    
}
