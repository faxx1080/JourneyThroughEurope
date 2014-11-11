/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import jte.game.City;

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
        
    }

    public void flyClick() {
        
    }

    public void winClick() {
        
    }
    
}
