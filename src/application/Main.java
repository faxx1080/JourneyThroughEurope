/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import jte.fxml.FXMLFiles;
import jte.ui.SplashDialog;

/**
 *
 * @author Frank
 */
public final class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLFiles fxmlInst = FXMLFiles.getInstance();
        
        FXMLLoader x = new FXMLLoader(fxmlInst.getClass().getResource("SplashDialog.fxml"));
        
        x.setController(new SplashDialog());
        x.load();
        
        Scene scene = new Scene(x.getRoot());
        
        stage.setScene(scene);
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
