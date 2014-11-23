/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte;

/**
 *
 * @author Frank
 */
public enum JTEPropertyType {
    UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME,DATA_PATH,
    RESOURCE_LOCATION, 
    
    HELP_FILE,
    
    //XML
    XML_CITIESSCH,XML_CITIESFILELOC,
    XML_CITIESROOT, XML_CITYNODE, XML_CITYNAME, XML_CITYCOLOR, XML_CITYX, XML_CITYY,
    XML_CITYID, XML_CITYDESC, XML_CITYFLIGHTLOC, XML_CITYFLIGHTLOCX, XML_CITYFLIGHTLOCY,
    XML_CITYYELLOW, XML_CITYGREEN , XML_CITYRED,
    
    XML_RTROOT, XML_RTCITY, XML_RTID, XML_RTLAND, XML_RTSEA, XML_RTSCH, XML_RTFILELOC,
    //End XML
    
    UI_RADIUS, NUM_CARDS,
    
    //FXML
    FXML_AB, FXML_EXT, FXML_SPL, FXML_PLSET, FXML_JUI, FXML_HIST,
    //End FXML
    
    //PlayerPos Image names
    IMG_PATH, IMG_PREFIX_HOME, IMG_PREFIX_LOC,IMG_EXT,
    
    //Plyaer pos offsets
    IMG_PLHOME_XOFF, IMG_PLHOME_YOFF, IMG_PLLOC_XOFF, IMG_PLLOC_YOFF,
    
    // Cards
    IMG_EXT_JPG, IMG_CD_GR,
       IMG_CD_YE,
       IMG_CD_RD,
       IMG_CDINST_RD,
       IMG_CDINST_GR,
       IMG_CDINST_YE,IMG_PREFIX_DICE,
    
       
    // Anim
       ANIM_DURATION,
    noothing;
}
