/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import application.JTEPropertyType;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import jte.util.ResourceLoader;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class DialogCreator {
    
    private static final int[] result = {-1};
    
    /**
     * This shows a dialog and awaits its close, returning the result of the
     * dialog. The result is dependant on which button is clicked, where
     * the buttons are defined by the textResponse array. The return
     * result is the int of the index of the button clicked, which is the index
     * of the "text" from textResponse that was chosen.
     * Returns -1 if closed without answering.
     * @param textTitle Title of the prompt
     * @param textPrompt Detailed prompt
     * @param textResponse List of responses.
     * @return 
     */
    public static int showFXDialog(String textTitle, String textPrompt,
            String[] textResponse) {
        result[0] = -1;
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(textTitle);
        Window primaryStage = null;
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        
        Button[] btnResps = new Button[textResponse.length];
        
        for (int[] i = {0}; i[0] < btnResps.length; i[0]++) {
            Button t = new Button(textResponse[i[0]]);
            t.setOnAction(e -> {
                result[0] = i[0];
                dialogStage.close();
            });
            btnResps[i[0]] = t;
        }
        
        optionPane.setSpacing(10.0);
        optionPane.getChildren().addAll(btnResps);
        Label prompt = new Label(textPrompt);
        exitPane.setCenter(prompt);
        exitPane.setBottom(optionPane);
        exitPane.resize(200, 100);
        Scene scene = new Scene(exitPane, 200, 100);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        
        return result[0];
    }
    
    public void showFXDialogFatal(String message, boolean quit) {
        String[] d = {ResourceLoader.getString(JTEPropertyType.STR_OK)};
        showFXDialog(ResourceLoader.getString(JTEPropertyType.STR_ERROR),
               message, d);
        System.exit(1);
    }
    
}
