/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jte.JTEPropertyType;
import jte.JTEResourceType;
import jte.fxml.FXMLFiles;
import jte.game.GameStateManager;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

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

    public void startGame(Stage stage) {
        int numPl = plset.getNumPlayers();
        String[] plNames = new String[numPl];
        boolean[] plCPU = new boolean[numPl];
        
        for (int i = 0; i < numPl; i++) {
            plNames[i] = plset.getPlayerName(i+1);
            plCPU[i] = plset.getCPU(i+1);
        }
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        double radius = Double.parseDouble(props.getProperty(JTEPropertyType.UI_RADIUS));
        int numCards = Integer.parseInt(props.getProperty(JTEPropertyType.NUM_CARDS));
        
        //Boilerplate for FXML
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        String toLoad = 
                props.getProperty(JTEPropertyType.FXML_JUI) + 
                props.getProperty(JTEPropertyType.FXML_EXT);        
        
        FXMLLoader fxmlL = new FXMLLoader(fxmlInst.getClass().getResource(toLoad));
        
        String resPath = 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);
        
        fxmlL.setResources(ResourceBundle.getBundle(resPath));
        JourneyUI e = new JourneyUI();
        GameStateManager gsm = new GameStateManager(numCards, radius, plNames, plCPU, e);
        e.setGSM(gsm);
        fxmlL.setController(e);
        
        try {
           fxmlL.load(); 
        } catch (IOException ex) {
            //DialogCreator.showFXDialogFatal(RLoad.getString(JTEPropertyType.STR_ERROR_TEXT_IO), true);
        }
        
        Scene scene = new Scene(fxmlL.getRoot());
        Stage stageN = new Stage();
        stageN.setTitle(RLoad.getString(JTEResourceType.STR_JTE));
        stageN.setScene(scene);
        
        stageN.setOnShown(ev -> {
            e.onShown();
        });
        
        stageN.setOnCloseRequest(ev -> {
            e.onClose(ev);
        });
        
        stage.close();
        stageN.show();
        
    }
    
}
