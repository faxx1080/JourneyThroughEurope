/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.fxml;

/**
 * This is a stub class that does nothing except hold memory location for
 * FXML Files. It is a singleton however.
 * @author Frank Migliorino
 */
public class FXMLFiles {
    
    private static FXMLFiles inst;
    
    private FXMLFiles() {}
    
    /**
     * Returns the fxml instance, solely for calling getClass in FXMLLoader.
     * @return 
     */
    public static FXMLFiles getInstance() {
        if (inst == null) {
            return (inst = new FXMLFiles());
        } else
            return inst;
    }
}
