/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.List;

/**
 * Indicates a whole instruction for a card.
 * @author Frank
 */
public class CardInstruction {
    private InstructionTypes instType;
    private int modifier;
    private int numRounds;
    private List<Integer> valueCityID;

    /**
     * Creates a card instruction with the specified parameters.
     */
    public CardInstruction(InstructionTypes instType, int modifier, int numRounds, List<Integer> valueCityID) {
        this.instType = instType;
        this.modifier = modifier;
        this.numRounds = numRounds;
        this.valueCityID = valueCityID;
    }

    /**
     * Gets the actual instruction.
     * @return 
     */
    public InstructionTypes getInstType() {
        return instType;
    }

    /**
     * Gets the numerical modifier associated with that instruction (e.g. how many spaces, etc.)
     * @return 
     */
    public int getModifier() {
        return modifier;
    }

    /**
     * Gets how many rounds the instruction applies for.
     * @return 
     */
    public int getNumRounds() {
        return numRounds;
    }

    /**
     * Gets any cities associated with this instruction as IDs.
     * @return 
     */
    public List<Integer> getValueCityID() {
        return valueCityID;
    }
    
    
    
}
