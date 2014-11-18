/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import jte.game.City;
import org.w3c.dom.Document;
import properties_manager.PropertiesManager;
import xml_utilities.XMLUtilities;

import static jte.JTEPropertyType.*;
import org.w3c.dom.Node;

import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import xml_utilities.XMLUtilities;

/**
 *
 * This class loads in city info from the specified XML file.
 * This does NOT load links between cities.
 * @author Frank Migliorino
 * 
 */
public class CityLoader {
    
    // THIS WILL HELP US PARSE THE XML FILES
    private XMLUtilities xmlUtil;
    
    // THIS IS THE SCHEMA WE'LL USE
    private File citySchema;
    
    public CityLoader(String schemaPath) {
        xmlUtil = new XMLUtilities();
        citySchema = new File(schemaPath);
        citiesList = new HashMap<>();
    }
    
    // private List<City> citiesList;
    private final Map<Integer, City> citiesList;
    
    // WE RETURN LIST HERE since HW5
    // Only asks for the city locations, not links.
    
    public Map<Integer, City> readCities(String filePath) throws IOException {
        File loadFile = new File(filePath);
                
        try {
            Document doc = xmlUtil.loadXMLDocument(loadFile.getAbsolutePath(),
                citySchema.getAbsolutePath());
            
            loadCities(doc);
            
            
        } catch (Exception e) {
            throw new IOException("XML Did not load properly.");
        }
        
        return citiesList;
        
    }
    
    private void loadCities(Document doc) throws Exception {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Node cityNode = doc.getElementsByTagName(props.getProperty(XML_CITIESROOT)).item(0);
        
        ArrayList<Node> cities = xmlUtil.getChildNodesWithName(cityNode, props.getProperty(XML_CITYNODE));
        
        
        for (int i = 0; i < cities.size(); i++) {
            Node city = cities.get(i);
            NamedNodeMap cityAttrib = city.getAttributes();
            // Pull attribs
            // if returned a null, then that node DNE.
            String Sid, color, ScoordX, ScoordY, name, desc;
            Sid = cityAttrib.getNamedItem(props.getProperty(XML_CITYID)).getNodeValue();
            color = cityAttrib.getNamedItem(props.getProperty(XML_CITYCOLOR)).getNodeValue();
            ScoordX = cityAttrib.getNamedItem(props.getProperty(XML_CITYX)).getNodeValue();
            ScoordY = cityAttrib.getNamedItem(props.getProperty(XML_CITYY)).getNodeValue();
            name = cityAttrib.getNamedItem(props.getProperty(XML_CITYNAME)).getNodeValue();
            Node descNode = cityAttrib.getNamedItem(props.getProperty(XML_CITYDESC));
            
            Color col;
            
            switch (color) {
                case "red":
                    col = Color.RED;
                    break;
                case "yellow":
                    col = Color.YELLOW;
                    break;
                case "green":
                    col = Color.GREEN;
                    break;
                default:
                    throw new IllegalArgumentException("Color not expected. Die.");
            }
            
            if (descNode == null) {desc = "";}
            else {desc = descNode.getNodeValue();}
            
            int id, coordX, coordY;
            id = Integer.parseInt(Sid);
            coordX = Integer.parseInt(ScoordX);
            coordY = Integer.parseInt(ScoordY);
            
            Point2D coord = new Point2D(coordX, coordY);
            
            //TODO: Fix values for flightMap, isHarbor, isAirport
            City nCity = new City(0, Point2D.ZERO, coord, id, desc, name, true, col);
            //citiesList.add(nCity);
            citiesList.put(id, nCity);
        }
        
    }
    
    
}
