/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * 
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class PlayerSetupDialog implements Initializable {
    @FXML
    protected ComboBox<Character> cbxNumPl;
    @FXML
    private Button btnGo;
    @FXML
    private Label lblCardsPlayer;
    @FXML
    protected TextField txtPL1;
    @FXML
    protected CheckBox chkCPU1;
    @FXML
    protected TextField txtPL2;
    @FXML
    protected CheckBox chkCPU2;
    @FXML
    protected TextField txtPL4;
    @FXML
    protected CheckBox chkCPU4;
    @FXML
    protected TextField txtPL3;
    @FXML
    protected CheckBox chkCPU3;
    @FXML
    protected TextField txtPL5;
    @FXML
    protected CheckBox chkCPU5;
    @FXML
    protected TextField txtPL6;
    @FXML
    protected CheckBox chkCPU6;
    
    private EventHandlerPLSetup eventhdr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        eventhdr = new EventHandlerPLSetup(this);
        cbxNumPl.setOnAction(e -> {
            eventhdr.numPlayersChanged();
        });
        
        btnGo.setOnAction(e -> {
            eventhdr.startGame();
        });
    }
    
}
