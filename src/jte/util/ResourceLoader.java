/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import application.JTEPropertyType;
import java.util.ResourceBundle;
import properties_manager.PropertiesManager;

/**
 * This class simplifies the getting of strings from resources.
 * @author Frank
 */
public class ResourceLoader {
    
    private static final ResourceBundle rb =
            ResourceBundle.getBundle(PropertiesManager.getPropertiesManager().
                    getProperty(JTEPropertyType.RESOURCE_LOCATION));
    
    private ResourceLoader() {}
    
    /**
     * Pulls the given property type's associated string from the main
     * resource bundle of the application.
     * @param stringName
     * @return The string.
     */
    public static String getString(JTEPropertyType stringName) {
        return rb.getString(stringName.toString());
    }
    
    /**
     * Gets the actual resource bundle used, for hooking into FXML.
     */
    public static ResourceBundle getRB() {
        return rb;
    }
    
}
