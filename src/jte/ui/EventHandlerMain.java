/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jte.JTEPropertyType;
import jte.JTEResourceType;
import jte.fxml.FXMLFiles;
import jte.game.City;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class EventHandlerMain {
    private JourneyUI ui;
    
    public EventHandlerMain(JourneyUI ui) {
        this.ui = ui;
    }
    
    public void gameBoardClick(MouseEvent ev) {
        Point2D actualClick = new Point2D(ev.getX(),ev.getY());
        City foundCity = ui.getGSM().getCityFromCoord(actualClick, null);
        if (foundCity != null) {
            ui.getGSM().getLog().appendText(foundCity.getName());
            ui.setTxtOutput(foundCity.getName() + "\n" + foundCity.getDesc());
        } else {
            ui.setTxtOutput("No city.");
        }
    }
    
    public void gameBoardMMove(MouseEvent ev) {
        Point2D actualClick = new Point2D(ev.getX(),ev.getY());
        int mx, my, sx, sy;
        mx = (int) ev.getX();
        my = (int) ev.getY();
        sx = (int) ev.getSceneX();
        sy = (int) ev.getSceneY();
        
        String out = "Scene: " + mx + ", " + my + "\n Mouse: " + sx + ", " + sy;
        
        double[] dist = new double[1];
        
        City foundCity = ui.getGSM().getCityFromCoord(actualClick, dist);
        if (foundCity != null) {
            out += "\n" + String.format("%.2f",dist[0]) +  "\n" + foundCity.getName();
        }
        
        ui.setMouseCoordsText(out);
    }

    public void aboutClick() {
        new EventHandlerSplash().allAbout();
    }

    public void historyClick() {
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String toLoad = 
                props.getProperty(JTEPropertyType.FXML_HIST) + 
                props.getProperty(JTEPropertyType.FXML_EXT);        
        
        FXMLLoader fxmlL = new FXMLLoader(fxmlInst.getClass().getResource(toLoad));
        
        String resPath = 
                PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);
        
        fxmlL.setResources(ResourceBundle.getBundle(resPath));
        //HistoryDialog d = new HistoryDialog();
        //fxmlL.setController(d);
        
        try {
           fxmlL.load(); 
        } catch (IOException e) {
            //DialogCreator.showFXDialogFatal(RLoad.getString(JTEPropertyType.STR_ERROR_TEXT_IO), true);
        }
       
        HistoryDialog d = fxmlL.getController();
        
        d.setText(ui.getGSM().getLog().getText());
        
        // d.initialize(null, null); // Completely safe
        
        Scene scene = new Scene(fxmlL.getRoot());
        Stage stageN = new Stage();
        stageN.setTitle(RLoad.getString(JTEResourceType.STR_HIST));
        stageN.setScene(scene);
        stageN.show();
    }

    public void flyClick() {
        
    }

    public void winClick() {
        String resPath
                = PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);

        ResourceBundle rb = ResourceBundle.getBundle(resPath);

        DialogCreator.showFXDialogMessage(rb.getString(JTEResourceType.STR_WIN_TITLE.toString()),rb.getString(JTEResourceType.STR_WIN.toString()));

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        String toLoad
                = props.getProperty(JTEPropertyType.FXML_SPL)
                + props.getProperty(JTEPropertyType.FXML_EXT);

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
        
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
        

    }

    // UI Methods HW6
    
    // public void 
    
}
