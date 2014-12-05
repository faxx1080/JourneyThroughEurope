/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.util;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static jte.Constants.DATA_PATH;
import static jte.Constants.PROPERTIES_SCHEMA_FILE_NAME;
import static jte.Constants.UI_PROPERTIES_FILE_NAME;
import jte.JTEPropertyType;
import jte.file.CityLoader;
import jte.file.CityRouteLoader;
import jte.game.City;
import jte.game.CityGraph;
import static jte.util.Dijkstra.getShortestPathTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
                UI_PROPERTIES_FILE_NAME);
        props.addProperty(JTEPropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                PROPERTIES_SCHEMA_FILE_NAME);
        props.addProperty(JTEPropertyType.DATA_PATH,
                DATA_PATH);
        props.loadProperties(UI_PROPERTIES_FILE_NAME,
                PROPERTIES_SCHEMA_FILE_NAME);
        
        System.out.println(props.getProperty(JTEPropertyType.DATA_PATH));
        
        System.out.println("readCities get data");
        String filePath = "data/cities.xml";
        CityLoader instance = new CityLoader("data/cities.xsd");
        Map<Integer, City> out = instance.readCities(filePath);
        filePath = "data/graphID.xml";
        
        CityRouteLoader inst2 = new CityRouteLoader("data/graphID.xsd", out);
        CityGraph expResult = null;
        CityGraph result = inst2.readCityNeighbors(filePath);
        
        
        List<Vertex<City>> cities = new ArrayList<>(180);
        
        for (int i = 0; i < 180; i++) {
            City x = out.get(i);
            cities.add(new Vertex(x));
        }
        
        for (int i = 0; i < 180; i++) {
            ArrayList<Edge> ed = new ArrayList<>();
            for (City k : result.neighbors.get(i)) {
                ed.add(new Edge(cities.get(k.getId()), 1));
            }
            for (City k : result.neighborsSea.get(i)) {
                ed.add(new Edge(cities.get(k.getId()), 6));
            }
            Edge[] x = new Edge[0];
            cities.get(i).adjacencies = ed.toArray(x);
        }
        
        
        Dijkstra.computePaths(cities.get(5));
        for (Vertex v : cities) {
            System.out.println("Distance from #5 to " + v + ": "
                    + v.minDistance);
            List<Vertex> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
        
    }
    
}
