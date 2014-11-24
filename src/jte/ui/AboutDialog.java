/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import jte.JTEPropertyType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

/**
 * FXML Controller class
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class AboutDialog implements Initializable {

    @FXML
    private WebView webView;
    
    @FXML
    private Parent root;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load text for webview
        loadPage(webView, JTEPropertyType.HELP_FILE);
    }
    
    private AboutDialog thisInst;
    
    public AboutDialog getInst() {
        if (thisInst != null) {return (thisInst = new AboutDialog());}
        else {return thisInst;}
    }
    
    @FXML
    public void aboutDialogClose(ActionEvent e) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        
    }
    
    /**
	 * This method loads the HTML page that corresponds to the fileProperty
	 * argument and puts it into the WebView argument for display.
	 * 
     * @param wbv WebView that displays loaded HTML.
	 * 
	 * @param fileProperty
	 *            The file property, whose name can then be retrieved from the
	 *            property manager.
	 */
	public void loadPage(WebView wbv, JTEPropertyType fileProperty) {
		// GET THE FILE NAME
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		String fileName = props.getProperty(JTEPropertyType.DATA_PATH) + 
                props.getProperty(fileProperty);
		try {
			// LOAD THE HTML INTO THE EDITOR PANE
			String fileHTML = jte.file.TextFileLoader.loadTextFile(fileName);
            wbv.getEngine().loadContent(fileHTML, "text/html");
		} catch (IOException ioe) {
			//DialogCreator.showFXDialogFatal(RLoad.getString(JTEPropertyType.STR_ERROR_TEXT_IO), true);
		}
	}
    
}
