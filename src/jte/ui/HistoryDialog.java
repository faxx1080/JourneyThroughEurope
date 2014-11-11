/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author Frank
 */
public class HistoryDialog implements Initializable {

    @FXML
    private Parent  root;
    @FXML
    private TextArea txtHistory;
    @FXML
    private Button btnClose;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setText(String theText) {
        txtHistory.setText(theText);
    }
    
    @FXML
    private void historyClose(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    
}
