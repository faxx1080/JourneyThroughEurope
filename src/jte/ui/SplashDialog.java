package jte.ui;

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

/**
 * This class is the SplashDialog. 
 * @author Frank
 */
public class SplashDialog implements Initializable {
    
    @FXML
    private Parent root;
    
    private EventHandlerSplash eventHdrSplash;
    
    
    private FXMLLoader fxmlL;
    public final void initializeDialog() {
       fxmlL.setController(this);
       try {
          fxmlL.load(); 
       } catch (IOException e) {
           DialogCreator.showFXDialog(null, null, textResponse)
       }
       
        
        Scene scene = new Scene(x.getRoot());
    }
    
    /**
     * THE FIRST LINE AFTER THIS CLASS MUST BE
     * object.setController(object);.
     * 
     */
    public SplashDialog () {
        eventHdrSplash = new EventHandlerSplash();
        
        //Load data from fxml
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        FXMLLoader fxmlL = new FXMLLoader(fxmlInst.getClass().getResource("SplashDialog.fxml"));
        
        fxmlL.setResources(ResourceBundle.getBundle("stringsLocalized"));
    }

    @FXML
    private void onActionStart(ActionEvent event) {
        System.out.println("Start");
        eventHdrSplash.splashGameSetupPL();
    }

    @FXML
    private void onActionLoad(ActionEvent event) {
        System.out.println("Load");
        eventHdrSplash.splashGameLoad();
    }

    @FXML
    private void onActionAbout(ActionEvent event) {
        System.out.println("About");
        eventHdrSplash.allAbout();
    }

    @FXML
    private void onActionQuit(ActionEvent event) {
        
        System.out.println("Quit");
        Stage stage = (Stage) root.getScene().getWindow();
        eventHdrSplash.allQuit(stage);
        
    }
    
}
