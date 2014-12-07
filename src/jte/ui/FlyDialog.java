/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import jte.game.City;
import jte.game.GameStateManager;

/**
 * FXML Controller class
 *
 * @author Frank
 */
public class FlyDialog implements Initializable {
    @FXML
    private StackPane root;
    @FXML
    private Label lblOut;
    @FXML
    private AnchorPane ancDrawHere;
    
    private City clickedCity;
    private JourneyUI ui;
    private List<City> fly2;
    private List<City> fly4;
    
    private City home;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    // Called later by shower class.
    public void init(JourneyUI ui) {
        this.ui = ui;
        home = ui.getGSM().getCurrentPlayer().getCurrentCity();
        fly2 = ui.getGSM().get2FlyCities(home);
        fly4 = ui.getGSM().get4FlyCities(home);
        
        fly2.stream().map((c) -> {
            Circle ci = new Circle(5);
            ci.setCenterX(c.getFlightMapLoc().getX());
            ci.setCenterY(c.getFlightMapLoc().getY());
            return ci;
        }).map((ci) -> {
            ci.setStroke( javafx.scene.paint.Color.GREEN );
            return ci;
        }).map((ci) -> {
            ci.setStrokeWidth(3);
            return ci;
        }).forEach((ci) -> {
            ancDrawHere.getChildren().add(ci);
        });
        
        if (ui.getGSM().getMovesLeft() <= 3 ) return;
        
        fly4.stream().map((c) -> {
            Circle ci = new Circle(5);
            ci.setCenterX(c.getFlightMapLoc().getX());
            ci.setCenterY(c.getFlightMapLoc().getY());
            return ci;
        }).map((ci) -> {
            ci.setStroke( javafx.scene.paint.Color.YELLOW );
            return ci;
        }).map((ci) -> {
            ci.setStrokeWidth(3);
            return ci;
        }).forEach((ci) -> {
            ancDrawHere.getChildren().add(ci);
        });
        
    }

    @FXML
    private void btnCancel(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    /**
     * Do perform fly check here.
     * @param event 
     */
    @FXML
    private void imgMouseClick(MouseEvent ev) {
        Point2D actualClick = new Point2D(ev.getX(),ev.getY());
        clickedCity = ui.getGSM().getCityFromCoord(actualClick, null, true);
        if (fly2.contains(clickedCity) || fly4.contains(clickedCity)) {
        ((Stage) root.getScene().getWindow()).close();}
    }
    
    /**
     * Null if canceled.
     * @return 
     */
    public City getCity() {
        return clickedCity;
    }
    
}
