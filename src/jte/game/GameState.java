/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

/**
 *
 * @author Frank
 */
public enum GameState {
    NOT_STARTED, READY_ROLL, INPUT_MOVE, GAME_OVER;
    
    public static GameState fromInt(int i) {
        switch (i) {
            case 0:
                return NOT_STARTED;
            case 1:
                return READY_ROLL;
            case 2:
                return INPUT_MOVE;
            case 3:
                return GAME_OVER;
        }
        return null;
    }
    
    public static int toInt (GameState gs) {
        switch (gs) {
            case NOT_STARTED:
                return 0;
            case READY_ROLL:
                return 1;
            case INPUT_MOVE:
                return 2;
            case GAME_OVER:
                return 3;
        }
        return -1;
    }
}
