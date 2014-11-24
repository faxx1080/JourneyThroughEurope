/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import jte.JTEPropertyType;
import jte.JTEResourceType;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jte.fxml.FXMLFiles;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class EventHandlerSplash {
    
    public void splashGameSetupPL(Stage stage) {
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String toLoad = 
                props.getProperty(JTEPropertyType.FXML_PLSET) + 
                props.getProperty(JTEPropertyType.FXML_EXT);        
        
        FXMLLoader fxmlL = new FXMLLoader(fxmlInst.getClass().getResource(toLoad));
        
        String resPath = 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);
        
        fxmlL.setResources(ResourceBundle.getBundle(resPath));
        PlayerSetupDialog d = new PlayerSetupDialog();
        fxmlL.setController(d);
        
        try {
           fxmlL.load(); 
        } catch (IOException e) {
            //DialogCreator.showFXDialogFatal(RLoad.getString(JTEPropertyType.STR_ERROR_TEXT_IO), true);
        }
       
        // d.initialize(null, null); // Completely safe
        
        Scene scene = new Scene(fxmlL.getRoot());
        Stage stageN = new Stage();
        stageN.setTitle(RLoad.getString(JTEResourceType.STR_PLAYERSETUP));
        stageN.setScene(scene);
        stageN.show();
        stage.close();
    }
    
    public void splashGameLoad() {
        
    }
    
//    public void allQuit() {
//        
//    }
    
    public void allQuit(Stage stage) {
//        ResourceBundle rb = ResourceBundle.getBundle("stringsLocalized");
//        String textTitle = rb.getString(JTEResourceType.DEF_KILL_TITLE.toString());
//        String textPrompt = rb.getString(JTEResourceType.DEF_ENSURE_EXIT.toString());
//        String[] exitopt = new String[2];
//        exitopt[0] = rb.getString(JTEResourceType.DEF_NO.toString());
//        exitopt[1] = rb.getString(JTEResourceType.DEF_YES.toString());
//        DialogResult result = DialogCreator.showFXDialogConfirm(textTitle, textPrompt, 
//                RLoad.getString(JTEResourceType.DEF_YES), 
//                RLoad.getString(JTEResourceType.DEF_NO));
//        
//        if (result.equals(result.RES_YES)){
//            stage.close();
//        }
        
        //"For a casual game like this we don't need to "
        //"verify any user action, so simply close everything"
        
        stage.close();
    }
    
    public void allQuit(JourneyUI ui) {
        
    }
    
    public void allAbout() {
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        String toLoad = 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.FXML_AB) + 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.FXML_EXT);        
        
        FXMLLoader fxmlL = new FXMLLoader(fxmlInst.getClass().getResource(toLoad));
                
        String resPath = 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);

        fxmlL.setResources(ResourceBundle.getBundle(resPath));
        fxmlL.setController(new AboutDialog());
        
        try {
           fxmlL.load(); 
        } catch (IOException e) {
            //DialogCreator.showFXDialogFatal(RLoad.getString(JTEPropertyType.STR_ERROR_TEXT_IO), true);
        }
       
        Scene scene = new Scene(fxmlL.getRoot());
        Stage stage = new Stage();
        stage.setTitle(RLoad.getString(JTEResourceType.STR_JTE));
        stage.setScene(scene);
        stage.show();
    }
    
    
}
