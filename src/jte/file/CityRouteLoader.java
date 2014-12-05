/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import java.io.IOException;
import java.util.List;
import jte.game.City;
import properties_manager.PropertiesManager;

import static jte.JTEPropertyType.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jte.game.CityGraph;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import xml_utilities.XMLUtilities;

/**
 *
 * This class loads in city info from the specified XML file.
 * This does NOT load links between cities.
 * @author Frank Migliorino
 * 
 */
public class CityRouteLoader {
    
    // THIS WILL HELP US PARSE THE XML FILES
    private XMLUtilities xmlUtil;
    
    // THIS IS THE SCHEMA WE'LL USE
    private File citySchema;
    
    private Map<Integer, City> cities;
    
    private Map<Integer, List<City>> neigh;
    private Map<Integer, List<City>> neighSea;
    
    private CityGraph cityNeighbors;
    
    public CityRouteLoader(String schemaPath, Map<Integer, City> cities) {
        xmlUtil = new XMLUtilities();
        citySchema = new File(schemaPath);
        citiesList = cities;
        
        neigh = new HashMap<>(citiesList.size());
        neighSea = new HashMap<>(citiesList.size());
        
        cityNeighbors = new CityGraph(neigh, neighSea);
        
    }
    
    private City lookupCity(int id) {
        return citiesList.get(id);
    }
    
    // private List<City> citiesList;
    private final Map<Integer, City> citiesList;
    
    
    public CityGraph readCityNeighbors(String filePath) throws IOException {
        File loadFile = new File(filePath);
                
        try {
            Document doc = xmlUtil.loadXMLDocument(loadFile.getAbsolutePath(),
                citySchema.getAbsolutePath());
            
            
            loadCities(doc);
            
        } catch (Exception e) {
            throw new IOException("XML Did not load properly.");
        }
        
        return cityNeighbors;
        
    }
    
    private void loadCities(Document doc) throws Exception {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Node rtNode = doc.getElementsByTagName(props.getProperty(XML_RTROOT)).item(0);
        
        ArrayList<Node> citiesNode = xmlUtil.getChildNodesWithName(rtNode, props.getProperty(XML_RTCITY));
        
        for (Node city : citiesNode) {
            
            Node cityID =  xmlUtil.getChildNodeWithName(city, props.getProperty(XML_RTID));
            
            int currentID = Integer.parseInt(cityID.getTextContent());
            
            try {
                Node cityLandNode = xmlUtil.getChildNodeWithName(city, props.getProperty(XML_RTLAND));
                ArrayList<Node> landRts = xmlUtil.getChildNodesWithName(cityLandNode, props.getProperty(XML_RTID));
                ArrayList<City> landCit = new ArrayList<>();
                
                for (Node l : landRts) {
                    landCit.add(lookupCity(Integer.parseInt(l.getTextContent())));
                }
                neigh.put(currentID, landCit);
            } catch (DOMException dOMException) {
            } catch (NumberFormatException numberFormatException) {
            }

            
            try {
                Node citySeaNode = xmlUtil.getChildNodeWithName(city, props.getProperty(XML_RTSEA));
                ArrayList<Node> seaRts = xmlUtil.getChildNodesWithName(citySeaNode, props.getProperty(XML_RTID));
                ArrayList<City> seaCit = new ArrayList<>();
                
                for (Node l : seaRts) {
                    seaCit.add(lookupCity(Integer.parseInt(l.getTextContent())));
                }
                neighSea.put(currentID, seaCit);
            } catch (DOMException dOMException) {
            } catch (NumberFormatException numberFormatException) {
            }
        }
        
    }
    
    
}
