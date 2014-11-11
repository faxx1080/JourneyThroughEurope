/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import jte.JTEPropertyType;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import jte.fxml.FXMLFiles;
import jte.ui.DialogCreator;
import jte.ui.SplashDialog;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

import static jte.Constants.*;
/**
 *
 * @author Frank
 */
public final class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(JTEPropertyType.UI_PROPERTIES_FILE_NAME,
                UI_PROPERTIES_FILE_NAME);
        props.addProperty(JTEPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                PROPERTIES_SCHEMA_FILE_NAME);
        props.addProperty(JTEPropertyType.DATA_PATH,
                DATA_PATH);
        props.loadProperties(UI_PROPERTIES_FILE_NAME,
                PROPERTIES_SCHEMA_FILE_NAME);
        
        String toLoad = 
                props.getProperty(JTEPropertyType.FXML_SPL) + 
                props.getProperty(JTEPropertyType.FXML_EXT);
        String resPath = 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);
        
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        FXMLLoader fxmlL = new FXMLLoader(fxmlInst.getClass().getResource(toLoad));
        
        fxmlL.setResources(ResourceBundle.getBundle(resPath));
        fxmlL.setController(new SplashDialog());
        try {
           fxmlL.load(); 
        } catch (IOException e) {
            //DialogCreator.showFXDialogFatal(RLoad.getString(JTEPropertyType.STR_ERROR_TEXT_IO), true);
        }
       
        Scene scene = new Scene(fxmlL.getRoot());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
