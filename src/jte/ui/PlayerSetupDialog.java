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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class PlayerSetupDialog implements Initializable {
    
    private final static int NUM_PLAYERS = 6;
    
    @FXML
    private Parent root;
    
    @FXML
    private ComboBox<Integer> cbxNumPl;
    @FXML
    private Button btnGo;
    @FXML
    private Label lblCardsPlayer;
    @FXML
    private TextField txtPL1;
    @FXML
    private CheckBox chkCPU1;
    @FXML
    private TextField txtPL2;
    @FXML
    private CheckBox chkCPU2;
    @FXML
    private TextField txtPL4;
    @FXML
    private CheckBox chkCPU4;
    @FXML
    private TextField txtPL3;
    @FXML
    private CheckBox chkCPU3;
    @FXML
    private TextField txtPL5;
    @FXML
    private CheckBox chkCPU5;
    @FXML
    private TextField txtPL6;
    @FXML
    private CheckBox chkCPU6;
    
    @FXML
    protected AnchorPane paneP2; 
    @FXML
    protected AnchorPane paneP3; 
    @FXML
    protected AnchorPane paneP4; 
    @FXML
    protected AnchorPane paneP5; 
    @FXML
    protected AnchorPane paneP6; 
    
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
            eventhdr.startGame((Stage) root.getScene().getWindow());
        });
        
        for (int i = 1; i <= NUM_PLAYERS; i++) {
            cbxNumPl.getItems().add(i);
        }
        cbxNumPl.setValue(NUM_PLAYERS);
    }
    
    /**
     * Gets number of players selected.
     */
    public int getNumPlayers() {
        return cbxNumPl.getValue();
    }
    
    /**
     * Gets if Player is a CPU. One-based.
     */
    public boolean getCPU(int playerNum) {
        switch (playerNum) {
             case 1:
                return chkCPU1.isSelected();
            case 2:
                return chkCPU2.isSelected();
            case 3:
                return chkCPU3.isSelected();
            case 4:
                return chkCPU4.isSelected();
            case 5:
                return chkCPU5.isSelected();
            case 6:
                return chkCPU6.isSelected();
            default:
                throw new IllegalArgumentException("Player number doesn't exist.");           
        }
    }
    
    /**
     * Gets the player's name. One-based.
     */
    public String getPlayerName(int playerNum) {
        switch (playerNum) {
            case 1:
                return getTextOverride(txtPL1);
            case 2:
                return getTextOverride(txtPL2);
            case 3:
                return getTextOverride(txtPL3);
            case 4:
                return getTextOverride(txtPL4);
            case 5:
                return getTextOverride(txtPL5);
            case 6:
                return getTextOverride(txtPL6);
            default:
                throw new IllegalArgumentException("Player number doesn't exist.");
                
        }
    }
    
    private String getTextOverride(TextField txt) {
        return (txt.getText().equals("")) ? txt.getPromptText() : txt.getText();
    }
    
    
    
}
