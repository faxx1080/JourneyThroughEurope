/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

/**
 * Indicates the types of card instructions.
 * @author Frank
 */
public enum InstructionTypes {
    MOVEON, GOTO, NEXTROUND_SCORE_ADD,
    NEXTROUND_SCORE_MULT,
    NEXTROUND_SCORE_SET,
    GOAGAIN,
    FORCEFLY,
    MISSTURN_MOVEON,
    ADD_CARD,
    MOVE_LIMITER;
    
    public static int typeToInt(InstructionTypes type) {
        switch (type) {
            case MOVEON:
                return 0;
            case GOTO:
                return 1;
            case NEXTROUND_SCORE_ADD:
                return 2;
            case NEXTROUND_SCORE_MULT:
                return 3;
            case NEXTROUND_SCORE_SET:
                return 4;
            case GOAGAIN:
                return 5;
            case FORCEFLY:
                return 6;
            case MISSTURN_MOVEON:
                return 7;
            case ADD_CARD:
                return 8;
            case MOVE_LIMITER:
                return 9;
            default:
                throw new IllegalArgumentException("InstructionTypes error: int not mapped to type.");
        }
    }
    
}
