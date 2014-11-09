/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class EventHandlerPLSetup {
    private PlayerSetupDialog plset;
    
    public EventHandlerPLSetup(PlayerSetupDialog pls) {
        plset = pls;
    }

    public void numPlayersChanged() {
        int numPl = plset.getNumPlayers();
        switch (numPl) {
            case 1:
                plset.paneP2.setVisible(false);
                plset.paneP3.setVisible(false);
                plset.paneP4.setVisible(false);
                plset.paneP5.setVisible(false);
                plset.paneP6.setVisible(false);
                return;
            case 2:
                plset.paneP2.setVisible(true);
                plset.paneP3.setVisible(false);
                plset.paneP4.setVisible(false);
                plset.paneP5.setVisible(false);
                plset.paneP6.setVisible(false);
                return;
            case 3:
                plset.paneP2.setVisible(true);
                plset.paneP3.setVisible(true);
                plset.paneP4.setVisible(false);
                plset.paneP5.setVisible(false);
                plset.paneP6.setVisible(false);
                return;
            case 4:
                plset.paneP2.setVisible(true);
                plset.paneP3.setVisible(true);
                plset.paneP4.setVisible(true);
                plset.paneP5.setVisible(false);
                plset.paneP6.setVisible(false);
                return;
            case 5:
                plset.paneP2.setVisible(true);
                plset.paneP3.setVisible(true);
                plset.paneP4.setVisible(true);
                plset.paneP5.setVisible(true);
                plset.paneP6.setVisible(false);
                return;
            case 6:
                plset.paneP2.setVisible(true);
                plset.paneP3.setVisible(true);
                plset.paneP4.setVisible(true);
                plset.paneP5.setVisible(true);
                plset.paneP6.setVisible(true);
        }
    }

    public void startGame() {
        
        
    }
    
}
