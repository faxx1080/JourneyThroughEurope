/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.game;

import java.util.Random;

/**
 *
 * @author Frank
 */
public class Dice {
    private int roll;
    private Random rnd;
    
    public Dice() {
        rnd = new Random();
    } 
    
    public void roll(){
        roll = rnd.nextInt(6) + 1;
    }
    
    public int getRoll() {
        return roll;
    }
}
