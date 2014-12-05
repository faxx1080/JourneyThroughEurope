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
    MOVE_LIMITER,THROWTWICE, NOTHING;
    
    public static InstructionTypes intToType(int x) {
        switch (x) {
            case 0:
                return MOVEON;
            case 1:
                return GOTO;
            case 2:
                return NEXTROUND_SCORE_ADD;
            case 3:
                return NEXTROUND_SCORE_MULT;
            case 4:
                return NEXTROUND_SCORE_SET;
            case 5:
                return GOAGAIN;
            case 6:
                return FORCEFLY;
            case 7:
                return MISSTURN_MOVEON;
            case 8:
                return ADD_CARD;
            case 9:
                return MOVE_LIMITER;
            case 10:
                return THROWTWICE;
            default:
                return NOTHING;
        }
    }
    
    /**
     * -1 indicates nothing.
     * @param type
     * @return 
     */
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
            case THROWTWICE:
                return 10;
            default:
                return -1;
        }
    }
    
}
