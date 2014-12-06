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
 * This class loads in flight info from the specified XML file.
 * This does NOT load links between cities.
 * @author Frank Migliorino
 * 
 */
public class FlightLoader {
    
    // THIS WILL HELP US PARSE THE XML FILES
    private XMLUtilities xmlUtil;
    
    // THIS IS THE SCHEMA WE'LL USE
    private File citySchema;
    
    private Map<Integer, List<Integer>> neigh;
    
    private CityGraph cityNeighbors;
    
    public FlightLoader(String schemaPath) {
        xmlUtil = new XMLUtilities();
        citySchema = new File(schemaPath);
        
        neigh = new HashMap<>();
    }
    
    public Map<Integer, List<Integer>> readAirportNeighbors(String filePath) throws IOException {
        File loadFile = new File(filePath);
                
        try {
            Document doc = xmlUtil.loadXMLDocument(loadFile.getAbsolutePath(),
                citySchema.getAbsolutePath());
            
            loadAirNeigh(doc);
            
        } catch (Exception e) {
            throw new IOException("XML Did not load properly.");
        }
        
        return neigh;
        
    }
    
    private void loadAirNeigh(Document doc) throws Exception {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Node flNode = doc.getElementsByTagName(props.getProperty(XML_FLROOT)).item(0);
        
        ArrayList<Node> mappingNode = xmlUtil.getChildNodesWithName(flNode, props.getProperty(XML_FLMAPPING));
        
        for (Node mapping : mappingNode) {
            
            Node nstid =  xmlUtil.getChildNodeWithName(mapping, props.getProperty(XML_RTID));
            
            int stid = Integer.parseInt(nstid.getTextContent());
            
            try {
                Node cityLandNode = xmlUtil.getChildNodeWithName(mapping, props.getProperty(XML_FPC4));
                ArrayList<Node> landRts = xmlUtil.getChildNodesWithName(cityLandNode, props.getProperty(XML_RTID));
                ArrayList<Integer> landCit = new ArrayList<>();
                
                for (Node l : landRts) {
                    landCit.add(Integer.parseInt(l.getTextContent()));
                }
                neigh.put(stid, landCit);
            } catch (DOMException | NumberFormatException dOMException) {
            }

        }
        
    }
    
    
}
