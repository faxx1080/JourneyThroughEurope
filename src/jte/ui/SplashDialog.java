/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jte.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Frank
 */
public class SplashDialog implements Initializable {
    
    @FXML
    private Parent root;
    
    public SplashDialog () {
        // Nothing yet.
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Still nothing.
    }    

    @FXML
    private void onActionStart(ActionEvent event) {
        System.out.println("Start");
    }

    @FXML
    private void onActionLoad(ActionEvent event) {
        System.out.println("Load");
    }

    @FXML
    private void onActionAbout(ActionEvent event) {
        System.out.println("About");
    }

    @FXML
    private void onActionQuit(ActionEvent event) {
        System.out.println("Quit");
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
    
}
