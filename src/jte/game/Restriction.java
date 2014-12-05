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
    private int v1;
    private int v2;
    private int c1;
    private int c2;

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

    
    
    
}
