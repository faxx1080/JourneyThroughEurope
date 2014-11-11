/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import jte.JTEPropertyType;
import jte.JTEResourceType;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import jte.fxml.FXMLFiles;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class DialogCreator {
    
    private static final DialogResult[] res = {DialogResult.RES_NO};
    
    /**
     * This shows a dialog and awaits its close, returning the result of the
     * dialog. The result is dependant on which button is clicked, where
     * the buttons are defined by the textResponse array. The return
     * result is the int of the index of the button clicked, which is the index
     * of the "text" from textResponse that was chosen.
     * Returns -1 if closed without answering.
     * @param textTitle Title of the prompt
     * @param textPrompt Detailed prompt
     * @return 
     */
    public static void showFXDialogMessage(String textTitle, String textPrompt) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(textTitle);
        Window primaryStage = null;
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        
        
        
        HBox optionPane = new HBox();
        
        String resPath
                = PropertiesManager.getPropertiesManager().getProperty(JTEPropertyType.RESOURCE_LOCATION);
        
        Button[] btnResps = new Button[1];
        btnResps[0] = new Button(ResourceBundle.getBundle(resPath).getString(JTEResourceType.STR_OK.toString()));
        btnResps[0].setOnAction(e -> {dialogStage.close();});
        
        optionPane.setSpacing(10.0);
        optionPane.getChildren().addAll(btnResps);
        Label prompt = new Label(textPrompt);
        exitPane.setCenter(prompt);
        exitPane.setBottom(optionPane);
        exitPane.resize(200, 100);
        Scene scene = new Scene(exitPane, 200, 100);
        // TODO FIX NAME
        String css = FXMLFiles.getInstance().getClass().getResource("Styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    
    public static DialogResult showFXDialogConfirm(String textTitle, String textPrompt,
            String yesText, String noText) {
        
        res[0] = DialogResult.RES_NO;
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(textTitle);
        Window primaryStage = null;
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        
        Button[] btnResps = new Button[2];
        btnResps[0] = new Button(yesText);
        btnResps[0].setOnAction(e -> {
            res[0] = DialogResult.RES_YES;
            dialogStage.close();
        });
        
        btnResps[1] = new Button(noText);
        btnResps[1].setOnAction(e -> {
            res[0] = DialogResult.RES_NO;
            dialogStage.close();
        });
        
        optionPane.setSpacing(10.0);
        optionPane.getChildren().addAll(btnResps);
        Label prompt = new Label(textPrompt);
        exitPane.setCenter(prompt);
        exitPane.setBottom(optionPane);
        exitPane.resize(200, 100);
        Scene scene = new Scene(exitPane, 200, 100);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        
        return res[0];
        
    }

    
}
