/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import jte.JTEPropertyType;
import jte.game.Player;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class JourneyUIHelper {
    
    JourneyUI ui;
    
    public JourneyUIHelper(JourneyUI ui) {
        this.ui = ui;
    }
    
    private Image loadImg(Player pl, JTEPropertyType imgPrefix, JTEPropertyType imgSuffix) {
        PropertiesManager props = properties_manager.PropertiesManager.getPropertiesManager();
        String imgpath =  props.getProperty(JTEPropertyType.IMG_PATH);
        String imgprefix = props.getProperty(imgPrefix);
        String imgpostfix = props.getProperty(imgSuffix);
        
        String imgLoc = imgpath + imgprefix + (ui.getGSM().getPlayerNum(pl)+1) + imgpostfix;
        
        File imgFile = new File(imgLoc);
        InputStream fileIn;
        try {
            fileIn = new FileInputStream(imgFile);
        } catch (FileNotFoundException ex) {
            ui.getErrorHDR().imgNotFound();
            // Kills vm
            return null;
        }
        
        return new Image(fileIn);
        
    }
    
    public Image loadPlLoc(Player pl) {
        return loadImg(pl, JTEPropertyType.IMG_PREFIX_LOC, JTEPropertyType.IMG_EXT );
    }
    
    public Image loadPlHome(Player pl) {
        return loadImg(pl, JTEPropertyType.IMG_PREFIX_HOME, JTEPropertyType.IMG_EXT);
    }
    
    public Image loadDice(int num) {
        PropertiesManager props = properties_manager.PropertiesManager.getPropertiesManager();
        String imgpath =  props.getProperty(JTEPropertyType.IMG_PATH);
        String imgprefix = props.getProperty(JTEPropertyType.IMG_PREFIX_DICE);
        String imgpostfix = props.getProperty(JTEPropertyType.IMG_EXT);
        
        String imgLoc = imgpath + imgprefix + (num) + imgpostfix;
        
        File imgFile = new File(imgLoc);
        InputStream fileIn;
        try {
            fileIn = new FileInputStream(imgFile);
        } catch (FileNotFoundException ex) {
            ui.getErrorHDR().imgNotFound();
            // Kills vm
            return null;
        }
        
        return new Image(fileIn);        
    }
    
    public Image loadCard(int cityID) {
        JTEPropertyType col;
        String cityC = ui.getGSM().getCityFromID(cityID).getColor();
        switch (cityC) {
            case "red":
                col = JTEPropertyType.IMG_CD_RD;
                break;
            case "yellow":
                col = JTEPropertyType.IMG_CD_YE;
                break;
            case "green":
                col = JTEPropertyType.IMG_CD_GR;
                break;
            default:
                throw new IllegalArgumentException("Color not expected. Die.");
        }
        
        PropertiesManager props = properties_manager.PropertiesManager.getPropertiesManager();
        String imgpath =  props.getProperty(JTEPropertyType.IMG_PATH);
        String imgprefix = props.getProperty(col);
        String imgpostfix = props.getProperty(JTEPropertyType.IMG_EXT_JPG);
        
        String imgLoc = imgpath + imgprefix + cityID + imgpostfix;
        
        File imgFile = new File(imgLoc);
        InputStream fileIn;
        try {
            fileIn = new FileInputStream(imgFile);
        } catch (FileNotFoundException ex) {
            ui.getErrorHDR().imgNotFound();
            // Kills vm
            return null;
        }
        
        return new Image(fileIn);          
    }
    
}
