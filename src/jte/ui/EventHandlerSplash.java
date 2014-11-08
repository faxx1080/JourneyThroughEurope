/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import application.JTEPropertyType;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 *
 * @author Frank
 */
public class EventHandlerSplash {
    
    public void splashGameSetupPL() {
        
    }
    
    public void splashGameLoad() {
        
    }
    
    public void allQuit() {
        
    }
    
    public void allQuit(Stage stage) {
        ResourceBundle rb = ResourceBundle.getBundle("stringsLocalized");
        String textTitle = rb.getString(application.JTEPropertyType.DEF_KILL_TITLE.toString());
        String textPrompt = rb.getString(application.JTEPropertyType.DEF_ENSURE_EXIT.toString());
        String[] exitopt = new String[2];
        exitopt[0] = rb.getString(JTEPropertyType.DEF_NO.toString());
        exitopt[1] = rb.getString(JTEPropertyType.DEF_YES.toString());
        int result = DialogCreator.showFXDialog(textTitle, textPrompt, exitopt);
        
        if (result == 2) {
            stage.close();
        }
    }
    
    public void allQuit(JourneyUI ui) {
        
    }
    
    public void allAbout() {
        
    }
    
    
}
