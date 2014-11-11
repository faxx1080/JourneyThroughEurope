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
public class JTELog {
    
    private String allText = "";
    
    public String getText() {
        return allText;
    }
    
    public void appendText(String tx) {
        allText += "\n" + tx;
    }
    
    public void clear() {
        allText = "";
    }
    
}
