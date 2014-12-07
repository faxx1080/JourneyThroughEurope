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
public class Restriction {
    private InstructionTypes type;
    private int v1; // turns left for #8
    private int v2; // turnsleft 
    private int c1;
    private int c2;
    private GameStateManager gsm;
    private boolean nextRound;

    public Restriction(InstructionTypes type, int v1, int v2, int c1, int c2) {
        this.type = type;
        this.v1 = v1;
        this.v2 = v2;
        this.c1 = c1;
        this.c2 = c2;
    }

    public InstructionTypes getType() {
        return type;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }
    
    private boolean moveon() {
       gsm.setMovesLeft(v1);
       v2--;
       return (v2 <= 0);
    }
    
    private boolean moveto() {
        gsm.getCurrentPlayer().setCurrentCity(gsm.getCityFromID(c1));
        return true;
    }
    
    private boolean scoreadd() {
        if (!nextRound) {
            nextRound = true;
            return false;
        }
        gsm.setMovesLeft(gsm.getMovesLeft() + v1);
        return (v2-- <= 0);
    }
    
    private boolean scoremult() {
        if (!nextRound) {
            nextRound = true; return false;
        }
        gsm.setMovesLeft(gsm.getMovesLeft() * v1);
        return (v2-- <= 0);
    }
    
    private boolean scoreset() {
        if (!nextRound) {
            nextRound = true; return false;
        } 
        gsm.setMovesLeft(v1);
        return (v2-- <= 0);
    }
    
    /**
     * Must run after roll.
     * @return 
     */
    private boolean goagain() {
        if (!nextRound) {
            nextRound = true; return false;
        } 
        gsm.setMovesLeft(v1);
        return (v2-- <= 0);
    }
    
    private boolean forcefly() {
        // Later
        return true;
    }
    
    private boolean missturnMoveon() {
        if (!nextRound) {
            nextRound = true; return false;
        }
        gsm.setMovesLeft(0);
        if (v1-- > 0)
            return false;
        gsm.setMovesLeft(v2);
        return true;
    }
    
    private boolean addcard() {
        gsm.getCurrentPlayer().addCard(gsm.getRedCard());
        return true;
    }
    
    // Nope! Just set moves == 1.
    private boolean movelimiter() {
        //Update UI with special text: Can only move one space.
        if (!nextRound) {
            nextRound = true; return false;
        }
        gsm.setMovesLeft(1);
        return true;
    }
    
    // If rolled 6, keep this.
    // If not 6, set six flag.
    private boolean throwtwice() {
        if (!nextRound) {
            nextRound = true; return false;
        }
        if (gsm.getSixFlag()) {
            // update ui
            return false;
            
        } else {
            // update ui
            gsm.setSixFlag(true);
            return true;
        }
    }
    /*
    public boolean execute(GameStateManager gsm) {return false;};
    
    public boolean executeNextRound() {return false;}; 
    
    public boolean executeNextRound() {
        switch (type) {
            case MOVEON:
                return moveon();
            case GOTO:
                return moveto();
            case NEXTROUND_SCORE_ADD:
                return scoreadd();
            case NEXTROUND_SCORE_MULT:
                return scoremult();
            case NEXTROUND_SCORE_SET:
                return scoreset();
            case GOAGAIN:
                return goagain();
            case FORCEFLY:
                return forcefly();
            case MISSTURN_MOVEON:
                return missturnMoveon();
            case ADD_CARD:
                return addcard();
            case MOVE_LIMITER:
                return movelimiter();
            case THROWTWICE:
                return throwtwice();
            default:
                return false;
        }
    }
    
    public boolean execute(GameStateManager gsm) {
        this.gsm = gsm;
        switch (type) {
            case MOVEON:
                return moveon();
            case GOTO:
                return moveto();
            case NEXTROUND_SCORE_ADD:
                return scoreadd();
            case NEXTROUND_SCORE_MULT:
                return scoremult();
            case NEXTROUND_SCORE_SET:
                return scoreset();
            case GOAGAIN:
                return goagain();
            case FORCEFLY:
                return forcefly();
            case MISSTURN_MOVEON:
                return missturnMoveon();
            case ADD_CARD:
                return addcard();
            case MOVE_LIMITER:
                return movelimiter();
            case THROWTWICE:
                return throwtwice();
            default:
                return false;
        }
    } */

    
    
    
}
