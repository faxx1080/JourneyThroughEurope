/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static jte.Constants.DATA_PATH;
import static jte.Constants.PROPERTIES_SCHEMA_FILE_NAME;
import static jte.Constants.UI_PROPERTIES_FILE_NAME;
import jte.JTEPropertyType;
import jte.game.City;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class CityLoaderTest {

    /**
     * Test of readCities method, of class CityLoader.
     */
    @Test
    public void testReadCities() throws Exception {
        
                PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(JTEPropertyType.UI_PROPERTIES_FILE_NAME,
                UI_PROPERTIES_FILE_NAME);
        props.addProperty(JTEPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                PROPERTIES_SCHEMA_FILE_NAME);
        props.addProperty(JTEPropertyType.DATA_PATH,
                DATA_PATH);
        props.loadProperties(UI_PROPERTIES_FILE_NAME,
                PROPERTIES_SCHEMA_FILE_NAME);
        
        System.out.println(props.getProperty(JTEPropertyType.DATA_PATH));
        
        System.out.println("readCities");
        String filePath = "data/cities.xml";
        CityLoader instance = new CityLoader("data/cities.xsd");
        Map<Integer, City> out = instance.readCities(filePath);
        System.out.println("done");
        
        // assertEquals(expResult, result);
    }
    
}
