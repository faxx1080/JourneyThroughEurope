package jte.ui;

import jte.JTEPropertyType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jte.fxml.FXMLFiles;
import jte.util.RLoad;

/**
 * This class is the SplashDialog. 
 * @author Frank
 */
public class SplashDialog implements Initializable {
    
    @FXML
    private Parent root;
    
    private EventHandlerSplash eventHdrSplash;
    
    
    /**
     * 
     */
    public SplashDialog () {
        eventHdrSplash = new EventHandlerSplash();
    }

    @FXML
    private void onActionStart(ActionEvent event) {
        // System.out.println("Start");
        eventHdrSplash.splashGameSetupPL((Stage) root.getScene().getWindow());
    }

    @FXML
    private void onActionLoad(ActionEvent event) {
        // System.out.println("Load");
        eventHdrSplash.splashGameLoad();
    }

    @FXML
    private void onActionAbout(ActionEvent event) {
        // System.out.println("About");
        eventHdrSplash.allAbout();
    }

    @FXML
    private void onActionQuit(ActionEvent event) {
        // System.out.println("Quit");
        Stage stage = (Stage) root.getScene().getWindow();
        eventHdrSplash.allQuit(stage);
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
}
