/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import jte.JTEPropertyType;
import jte.JTEResourceType;
import jte.game.City;
import jte.game.GameStateManager;
import jte.game.Player;
import jte.util.RLoad;
import properties_manager.PropertiesManager;

/**
 * FXML Controller class
 *
 * @author Frank Migliorino <frank.migliorino@stonybrook.edu>
 */
public class JourneyUI implements Initializable {
    
    // <editor-fold desc="local fields">
    
    @FXML
    private StackPane root;
    @FXML
    private ScrollPane scp;
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
    @FXML
    private ImageView gameboardImg;
    @FXML
    private Label lblTopMsg;
    
    private GameStateManager gsm;
    private final EventHandlerMain eventhdr;
    private final ErrorHandler errhdr;
    private JourneyUIHelper juiHelper;
    
    private int plLocXOff;
    private int plLocYOff;
    private int durationMili;
    
    private Point2D plLocOff;
    
    // Player Home Spots
    protected ImageView[] plh;
    
    // Player Pos Spots
    protected ImageView[] plloc;
    
    //Player cards
    private List<List<ImageView>> plCardSmall;
    
    private UIState state = UIState.NO_CLICK;
    
    
    // </editor-fold>
    
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
    
    protected UIState getState() {
        return state;
    }
    
    protected void setState(UIState state) {
        this.state = state;
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
        
        int numPl = gsm.getNumPlayers();
        
        // plCardsAcc.getPanes().remove(pl6Title);
        if (numPl < 6) {
            plCardsAcc.getPanes().remove(numPl, 6);
        }
        juiHelper = new JourneyUIHelper(this);
        plh = new ImageView[gsm.getNumPlayers()];
        plloc = new ImageView[gsm.getNumPlayers()];
        
        PropertiesManager props = properties_manager.PropertiesManager.getPropertiesManager();
        plLocXOff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLLOC_XOFF));
        plLocXOff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLLOC_YOFF));
        durationMili = Integer.parseInt(props.getProperty(JTEPropertyType.ANIM_DURATION));
        
        plLocOff = new Point2D(plLocXOff, plLocYOff);
    }
    
    public void onShown() {
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
        Image plImgLoc = juiHelper.loadPlLoc(pl);
        int num = gsm.getPlayerNum(pl);
        ImageView plImgV = new ImageView(plImgHome);
        
        ImageView plImgL = new ImageView(plImgLoc);
        plImgL.setId(String.valueOf(num));
        plh[num] = plImgV;
        plloc[num] = plImgL;
        ancDrawPlayersHere.getChildren().add(plImgV);
        ancDrawPlayersHere.getChildren().add(plImgL);
        // Sets 0, 0 to x, y.
        // We need 0, 0 -> x-43, y-88
        PropertiesManager props = properties_manager.PropertiesManager.getPropertiesManager();
        int xoff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLHOME_XOFF));
        int yoff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLHOME_YOFF));
                
        plImgV.relocate(plPos.getX() + xoff, plPos.getY() + yoff);
        
        xoff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLLOC_XOFF));
        yoff = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLLOC_YOFF));
        
        plImgL.setTranslateX(plPos.getX() + xoff);
        plImgL.setTranslateY(plPos.getY() + yoff);
        
        //plImgL.relocate(plPos.getX() + xoff, plPos.getY() + yoff);
        plImgL.setOnMouseClicked(e -> {
            eventhdr.playerImageClick(e);
        });

    }
    
    public void setDice(int diceRoll) {
        Image dice = juiHelper.loadDice(diceRoll);
        imgDice.setImage(dice);
    }
    
    private TitledPane getTiledPanePl(int pl) {
        TitledPane tp;
        switch (pl) {
            case 0:
                tp = pl1Title;
                break;
            case 1:
                tp = pl2Title;
                break;
            case 2:
                tp = pl3Title;
                break;
            case 3:
                tp = pl4Title;
                break;
            case 4:
                tp = pl5Title;
                break;
            case 5:
                tp = pl6Title;
                break;
            default:
                return null;
        }
        return tp;
    }
    
    public void activatePlayer(int pl) {
        plCardsAcc.setExpandedPane(getTiledPanePl(pl));
        // Scrolling
        Point2D locPl = getGSM().getCurrentPlayer().getCurrentCity().getCoord();
        double y = gameboardImg.getImage().getHeight();
        double x = gameboardImg.getImage().getWidth();
        scp.setHvalue(locPl.getX() / x);
        scp.setVvalue(locPl.getY() / y);
        lblTopMsg.setText(getGSM().getCurrentPlayer().getName() + " "+ RLoad.getString(JTEResourceType.STR_GO) + " " + gsm.getMovesLeft()
        + " "+ RLoad.getString(JTEResourceType.STR_MOVELEFT));
        // Clear all lines
        ancDrawStuffHere.getChildren().clear();
        //Add new lines
        Point2D home = gsm.getCurrentPlayer().getCurrentCity().getCoord();
        Point2D next;
        List<City> cityLand = gsm.getCityNeigh(gsm.getCurrentPlayer().getCurrentCity());
        List<City> citySea  = gsm.getCityNeighSea(gsm.getCurrentPlayer().getCurrentCity());
        for (City c: cityLand) {
            next = c.getCoord();
            Line l = new Line();
            l.setStartX(home.getX());
            l.setStartY(home.getY());
            l.setEndX(next.getX());
            l.setEndY(next.getY());
            l.setStroke(Color.RED);
            ancDrawStuffHere.getChildren().add(l);
        }
        for (City c: citySea) {
            next = c.getCoord();
            Line l = new Line();
            l.setStartX(home.getX());
            l.setStartY(home.getY());
            l.setEndX(next.getX());
            l.setEndY(next.getY());
            l.setStroke(Color.BLUE);
            ancDrawStuffHere.getChildren().add(l);
        }
        
    }
    
    /**
     * For current player
     * @return 
     */
    private Point2D getPlayerCoord() {
        return getPlayerCoord(getGSM().getCurrentPlayer());
    }
    
    private Point2D getPlayerCoord(Player pl) {
        return pl.getCurrentCity().getCoord().add(plLocOff);
    }
    
    /**
     * For current player
     * @param pl
     * @param cityNew
     * @param cityOld 
     */
    public void movePlayerUI(City cityNew, City cityOld) {
        Point2D cityNewC = cityNew.getCoord();
        Point2D cityOldC = cityOld.getCoord();
        int pl = gsm.getPlayerNum(gsm.getCurrentPlayer());
        ImageView plImg = plloc[pl];
        // plImgLoc: (0,0)+(xoff,yoff)
        // 
        
        Point2D start = cityOldC.add(plLocOff);
        Point2D end = cityNewC.add(plLocOff);
        Point2D diff = end.subtract(start);
        // Start loc: cityOld.getCoord + (xoff, yoff)
        // End loc: cityNew.getCoord + (xoff, yoff)
        TranslateTransition movePlTrans = new TranslateTransition(Duration.millis(durationMili), plImg);
        movePlTrans.setByX(diff.getX());
        movePlTrans.setByY(diff.getY());
        
        
        // Timeline scrollT = ScrollTransition.getTimeline(scp, Duration.millis(durationMili), 0, 0);
        
        
        movePlTrans.play();
        
    }
    
    private void scrollTo() {}
    
    private void endMovePlayerUI() {
        
    }

    enum UIState {
        NO_CLICK, CLICK_PLAYER;
    }
    
    
    
}
