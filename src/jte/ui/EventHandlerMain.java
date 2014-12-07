/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import jte.JTEPropertyType;
import jte.JTEResourceType;
import jte.fxml.FXMLFiles;
import jte.game.City;
import jte.game.Player;
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
    
    public void debugGameBoardClick(MouseEvent ev) {
        Point2D actualClick = new Point2D(ev.getX(),ev.getY());
        City foundCity = ui.getGSM().getCityFromCoord(actualClick, null);
        if (foundCity != null) {
            ui.getGSM().getLog().appendText(foundCity.getName());
            ui.setTxtOutput(foundCity.getName() + "\n" + foundCity.getDesc());
        } else {
            ui.setTxtOutput("No city.");
        }
        
    }
    
    public void debugGameBoardMMove(MouseEvent ev) {
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
    
    /*
    ImgView < AnchorPane with Pl icons
    Property: lastClick.
    playerHome, none.
    Flow: V1: Click once on player icon
    set lastClick playerHome
    
    Click on city
    if lastclick none, exit
    else, movePlayer clickCity
    
    
    
    */
    public void playerImageClick(MouseEvent ev) {
        
    }
    
    public boolean gameBoardClick(MouseEvent ev) {
        Point2D actualClick = new Point2D(ev.getX(),ev.getY());
        City foundCity = ui.getGSM().getCityFromCoord(actualClick, null);
        if (foundCity != null) {
            ui.getGSM().movePlayer(foundCity);
            ui.getGSM().nextIteration();
            return true;
        } else {
            ui.getGSM().nextIteration();
            return false;
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
            out += "\n" + String.format("%.2f",dist[0]) +  "\n" + foundCity.getName() + "\n" + foundCity.getId();
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
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String toLoad = 
                props.getProperty(JTEPropertyType.FXML_FLYDG) + 
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
            //e.printStackTrace();
        }
       
        FlyDialog d = fxmlL.getController();
        d.init(ui);
        
        Scene scene = new Scene(fxmlL.getRoot());
        Stage stageN = new Stage();
        stageN.setScene(scene);
        
        stageN.initModality(Modality.WINDOW_MODAL);
        
        stageN.initOwner(ui.getStage());
        
        stageN.showAndWait();
        
        City moveTo = d.getCity();
        
        if (moveTo != null) {
            // actually move!
            ui.getGSM().movePlayer(moveTo, true);
        }
        ui.getGSM().nextIteration();
        
    }

    public void winClick(Player pl) {
        String resPath
                = PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);

        ResourceBundle rb = ResourceBundle.getBundle(resPath);

        DialogCreator.showFXDialogMessage(rb.getString(JTEResourceType.STR_WIN_TITLE.toString())
                , pl.getName() + " " + rb.getString(JTEResourceType.STR_WIN.toString()));

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
