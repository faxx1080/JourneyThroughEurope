/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jte.Constants;
import jte.JTEPropertyType;
import static jte.JTEPropertyType.DATA_PATH;
import static jte.JTEPropertyType.XML_FLFILELOC;
import static jte.JTEPropertyType.XML_FLSCH;
import jte.file.CityLoader;
import jte.file.CityRouteLoader;
import jte.file.FlightLoader;
import jte.game.CPUData;
import jte.game.City;
import jte.game.CityGraph;
import org.junit.Test;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class DijkstraTest {

    @Test
    public void testMain() throws Exception {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(JTEPropertyType.UI_PROPERTIES_FILE_NAME,
                Constants.UI_PROPERTIES_FILE_NAME);
        props.addProperty(JTEPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                Constants.PROPERTIES_SCHEMA_FILE_NAME);
        props.addProperty(JTEPropertyType.DATA_PATH,
                Constants.DATA_PATH);
        props.loadProperties(Constants.UI_PROPERTIES_FILE_NAME,
                Constants.PROPERTIES_SCHEMA_FILE_NAME);
        
        System.out.println(props.getProperty(JTEPropertyType.DATA_PATH));
        
        System.out.println("readCities get data");
        String filePath = "data/cities.xml";
        
        CityLoader instance = new CityLoader("data/cities.xsd");
        
        Map<Integer, City> cityToID = instance.readCities(filePath);
        
        filePath = "data/graphID.xml";
        
        CityRouteLoader inst = new CityRouteLoader("data/graphID.xsd", cityToID);
        
        CityGraph cityNeigh = inst.readCityNeighbors(filePath);
        
        String schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_FLSCH);
        String neighPath = props.getProperty(DATA_PATH) + props.getProperty(XML_FLFILELOC);
        
        Map<Integer, List<Integer>> flQuadToQuads = new FlightLoader(schemaPath).readAirportNeighbors(neighPath);
        Map<Integer, List<City>> flQuadToCities = new HashMap<>();
        
        for(int i = 0; i<=6; i++) {
            flQuadToCities.put( i , new ArrayList<>(180));
        }
        
        cityToID.values().stream().forEach((c) -> {
            flQuadToCities.get(c.getFlightLoc()).add(c);
        });

        CPUData cp = new CPUData(cityToID, cityNeigh, flQuadToQuads, flQuadToCities);
        ArrayList<City> c = null;
        //c.add(cityToID.get(168));
        int z = 0;
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 180; j++) {
                List<City> temp = cp.getShortestPathTo(cityToID.get(i), cityToID.get(j), 1, c);
                //System.out.println(temp);
                z++;
            }
        }
        
    }
    
}
