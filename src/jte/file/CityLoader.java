/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import java.io.IOException;
import jte.game.City;
import properties_manager.PropertiesManager;

import static jte.JTEPropertyType.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
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
            String Sid, color, ScoordX, ScoordY, name, desc, flightQuad;
            int fpX, fpY, type, v1, v2, c1, c2;
            
            Sid = cityAttrib.getNamedItem(props.getProperty(XML_CITYID)).getNodeValue();
            color = cityAttrib.getNamedItem(props.getProperty(XML_CITYCOLOR)).getNodeValue();
            ScoordX = cityAttrib.getNamedItem(props.getProperty(XML_CITYX)).getNodeValue();
            ScoordY = cityAttrib.getNamedItem(props.getProperty(XML_CITYY)).getNodeValue();
            name = cityAttrib.getNamedItem(props.getProperty(XML_CITYNAME)).getNodeValue();
            flightQuad = cityAttrib.getNamedItem(props.getProperty(XML_CITYFLQUAD)).getNodeValue();
            Node descNode = cityAttrib.getNamedItem(props.getProperty(XML_CITYDESC));
            Node nfpX, nfpY, ntype, nv1, nv2, nc1, nc2;
            nfpX = cityAttrib.getNamedItem(props.getProperty(XML_CITYFX));
            nfpY = cityAttrib.getNamedItem(props.getProperty(XML_CITYFY));
            ntype = cityAttrib.getNamedItem(props.getProperty(XML_CITYTYPE));
            nv1 = cityAttrib.getNamedItem(props.getProperty(XML_CITYTV1));
            nv2 = cityAttrib.getNamedItem(props.getProperty(XML_CITYTV2));
            nc1 = cityAttrib.getNamedItem(props.getProperty(XML_CITYTC1));
            nc2 = cityAttrib.getNamedItem(props.getProperty(XML_CITYTC2));
            
            if (descNode == null) {desc = "";}      
            else {desc = descNode.getNodeValue();}
            
            //Flight plan
            fpX = 0; fpY = 0;
            boolean isAirport; int flightQuadNum;
            try {
                flightQuadNum = Integer.parseInt(flightQuad);
                fpX = Integer.parseInt(nfpX.getNodeValue());
                fpY = Integer.parseInt(nfpY.getNodeValue());
            } catch (Exception e) {
                flightQuadNum = 0;
            }
            isAirport = (flightQuadNum > 0 && flightQuadNum < 7);
            
            //instructions
            type = -1;
            v1 = -1; v2 = -1; c1 = -1; c2 = -1;
            if (ntype != null) {
                try {
                    type = Integer.parseInt(ntype.getNodeValue());
                    if (nv1 != null) {
                        v1 = Integer.parseInt(nv1.getNodeValue());
                    }
                    if (nv2 != null) {
                        v2 = Integer.parseInt(nv2.getNodeValue());
                    }
                    if (nc1 != null) {
                        c1 = Integer.parseInt(nc1.getNodeValue());
                    }
                    if (nc2 != null) {
                        c2 = Integer.parseInt(nc2.getNodeValue());
                    }
                } catch (DOMException | NumberFormatException e) {}
            }
            
            int id, coordX, coordY;
            id = Integer.parseInt(Sid);
            coordX = Integer.parseInt(ScoordX);
            coordY = Integer.parseInt(ScoordY);
            
            Point2D coord = new Point2D(coordX, coordY);
            Point2D fcord = new Point2D(-100, -100); //not possible to click
            if (isAirport) {
                fcord = new Point2D(fpX, fpY);
            }
            
            City nCity = new City(0, Point2D.ZERO, coord, id, desc, name, isAirport, color, flightQuadNum, fcord, type, v1, v2, c1, c2);
            
            //citiesList.add(nCity);
            citiesList.put(id, nCity);
        }
        
    }
    
    
}
