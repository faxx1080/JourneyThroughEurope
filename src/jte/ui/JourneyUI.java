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
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class JourneyUI implements Initializable {
    @FXML
    private StackPane root;
    @FXML
    private TitledPane pl1Title;
    @FXML
    private VBox pl1Cards;
    @FXML
    private TitledPane pl2Title;
    @FXML
    private VBox pl2Cards;
    @FXML
    private TitledPane pl3Title;
    @FXML
    private VBox pl3Cards;
    @FXML
    private TitledPane pl4Title;
    @FXML
    private VBox pl4Cards;
    @FXML
    private TitledPane pl5Title;
    @FXML
    private VBox pl5Cards;
    @FXML
    private TitledPane pl6Title;
    @FXML
    private VBox pl6Cards;
    @FXML
    private TextArea txtOutput;
    @FXML
    private ImageView imgDice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void onMouseClickedHW5(MouseEvent event) {
        
    }
    
}
