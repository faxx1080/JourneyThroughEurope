/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jte.JTEPropertyType;
import jte.game.City;
import jte.game.GameStateManager;
import jte.game.Player;
import properties_manager.PropertiesManager;

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
    private Label mouseCoords;
    @FXML
    private ImageView imgDice;
    @FXML
    private AnchorPane ancDrawStuffHere;
    @FXML
    private AnchorPane ancDrawPlayersHere;
    @FXML
    private AnchorPane ancDrawCardsAnim;
    @FXML
    private Accordion plCardsAcc;

    private GameStateManager gsm;
    private final EventHandlerMain eventhdr;
    private final ErrorHandler errhdr;
    private JourneyUIHelper juiHelper;
    
    // Player Home Spots
    protected ImageView[] plh;
    
    // Player Pos Spots
    protected ImageView[] plloc;
    
    //Player cards
    private List<List<ImageView>> plCardSmall;
    
    
    public JourneyUI() {
        eventhdr = new EventHandlerMain(this);
        errhdr = new ErrorHandler(this);
    }
    
    public void setGSM(GameStateManager gsm) {
        this.gsm = gsm;
    }
    
    protected GameStateManager getGSM() {
        return gsm;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pl1Title.setText(gsm.getPlayerName(1));
        pl2Title.setText(gsm.getPlayerName(2));
        pl3Title.setText(gsm.getPlayerName(3));
        pl4Title.setText(gsm.getPlayerName(4));
        pl5Title.setText(gsm.getPlayerName(5));
        pl6Title.setText(gsm.getPlayerName(6));
        juiHelper = new JourneyUIHelper(this);
        plh = new ImageView[gsm.getNumPlayers()];
        gsm.initGameAndCards();
        
    }
    
    public void setTxtOutput(String text) {
        txtOutput.setText(text);
    }
    
    public String getTxtOutput() {
        return txtOutput.getText();
    }
    
    public void setMouseCoordsText(String text) {
        mouseCoords.setText(text);
    }
    
    public String getMouseCoordsText() {return mouseCoords.getText();}
    
    @FXML
    private void gameBoardClick(MouseEvent event) {
        eventhdr.gameBoardClick(event);
    }
    
    @FXML
    private void gameBoardMMove(MouseEvent event) {
        eventhdr.gameBoardMMove(event);
    }

    @FXML
    private void aboutClick(MouseEvent event) {
        eventhdr.aboutClick();
    }

    @FXML
    private void historyClick(MouseEvent event) {
        eventhdr.historyClick();
    }

    @FXML
    private void flyClick(MouseEvent event) {
        eventhdr.flyClick();
    }

    @FXML
    private void winClick(MouseEvent event) {
        eventhdr.winClick();
        ((Stage) root.getScene().getWindow()).close();
    }
    
    public ErrorHandler getErrorHDR() {
        return errhdr;
    }
    
    /**
     * Zero based.
     * @param plNum
     * @param card 
     */
    public void addCard(int plNum, City card) {
        TitledPane tp; VBox vb;
        switch (plNum) {
            case 0:
                tp = pl1Title;
                vb = pl1Cards;
                break;
            case 1:
                tp = pl2Title;
                vb = pl2Cards;
                break;
            case 2:
                tp = pl3Title;
                vb = pl3Cards;
                break;
            case 3:
                tp = pl4Title;
                vb = pl4Cards;
                break;
            case 4:
                tp = pl5Title;
                vb = pl5Cards;
                break;
            case 5:
                tp = pl6Title;
                vb = pl6Cards;
                break;
            default:
                return;
        }
        plCardsAcc.setExpandedPane(tp);
        // Add imageview to vbox, getX, Y loc, setVisible False
        // remove from there
        // add to root anchorpane
        ImageView toAdd = new ImageView(juiHelper.loadCard(card.getId()));
        toAdd.setFitWidth(200);
        toAdd.setPreserveRatio(true);
        vb.getChildren().add(toAdd);
    }

    /**
     * Zero based.
     * @param plNum
     * @param card 
     */
    public void removeCard(int plNum, City card) {
        ImageView x;
        
    }
    
    // Events from GSM
    
    public void uiInitPlayer(Player pl) {
        Point2D plPos = pl.getHomeCity().getCoord();
        // Add constant for img size.
        Image plImgHome = juiHelper.loadPlHome(pl);
        int num = gsm.getPlayerNum(pl);
        ImageView plImgV = new ImageView(plImgHome);
        plh[num] = plImgV;
        ancDrawPlayersHere.getChildren().add(plImgV);
        // Sets 0, 0 to x, y.
        // We need 0, 0 -> x-43, y-88
        PropertiesManager props = properties_manager.PropertiesManager.getPropertiesManager();
        int xoff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLHOME_XOFF));
        int yoff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLHOME_YOFF));
        
        plImgV.relocate(plPos.getX() + xoff, plPos.getY() + yoff);
    }
    
    public void setDice(int diceRoll) {
        Image dice = juiHelper.loadDice(diceRoll);
        imgDice.setImage(dice);
    }
    
    
    
}
