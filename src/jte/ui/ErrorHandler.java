/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

/**
 *
 * @author Frank
 */
public class ErrorHandler {
    private JourneyUI ui;
    
    public ErrorHandler(JourneyUI ui) {
        this.ui = ui;
    }
    
    public void imgNotFound() {
        System.exit(1);
    }
    
}
