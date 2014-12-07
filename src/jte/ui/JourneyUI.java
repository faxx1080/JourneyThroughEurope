/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import com.sun.javafx.tk.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
    @FXML
    private Button flyButton; 
    
    // Buffer a list of translate transitions.
    
    private List<TranslateTransition> transitionCards;
    
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
        transitionCards = new ArrayList<>();
    }
    
    public void onShown() {
        gsm.initGameAndCards();
    }
    
    public void animateCards() {
        SequentialTransition seq = new SequentialTransition();
        seq.getChildren().addAll(transitionCards);
        seq.setOnFinished(e -> {gsm.continueInit();});
        seq.play();
    }
    
    public void setTxtOutput(String text) {
        txtOutput.setText(text);
    }
    
    public void setMovesLeft(int m) {
        lblTopMsg.setText(getGSM().getCurrentPlayer().getName() + " "+ RLoad.getString(JTEResourceType.STR_GO) + " " + gsm.getMovesLeft()
        + " "+ RLoad.getString(JTEResourceType.STR_MOVELEFT));
        
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
    private void uiSave() {
        gsm.save();
    }
    
    @FXML
    private void historyClick(MouseEvent event) {
        eventhdr.historyClick();
    }

    @FXML
    private void flyClick(MouseEvent event) {
        eventhdr.flyClick();
    }
    
    public Stage getStage() {
        return ((Stage) root.getScene().getWindow());
    }
    
    public ErrorHandler getErrorHDR() {
        return errhdr;
    }
    
    public void addCard2(int plNum, City card) {
        TitledPane tp; final VBox vb;
        Color col;
        switch (plNum) {
            case 0:
                tp = pl1Title;
                vb = pl1Cards;
                col = Color.BLACK;
                break;
            case 1:
                tp = pl2Title;
                vb = pl2Cards;
                col = Color.BLUE;
                break;
            case 2:
                tp = pl3Title;
                vb = pl3Cards;
                col = Color.GREEN;
                break;
            case 3:
                tp = pl4Title;
                vb = pl4Cards;
                col = Color.RED;
                break;
            case 4:
                tp = pl5Title;
                vb = pl5Cards;
                col = Color.WHITE;
                break;
            case 5:
                tp = pl6Title;
                vb = pl6Cards;
                col = Color.YELLOW;
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
        
        Point2D pointTo = toAdd.localToScene(Point2D.ZERO);
        
        vb.getChildren().remove(toAdd);
        
        toAdd.setOnMouseClicked(ev -> {
            Point2D locPl = card.getCoord();
            double y = gameboardImg.getImage().getHeight();
            double x = gameboardImg.getImage().getWidth();
            scp.setHvalue(locPl.getX() / x);
            scp.setVvalue(locPl.getY() / y);
        });
        
        //Add x marks spot
        Point2D cpt = card.getCoord();
        
        final double len = 10;
        
        Point2D cptMajT = cpt.add(-len,len);
        Point2D cptMajB = cpt.add(len,-len);
        Point2D cptMinT = cpt.add(len,len);
        Point2D cptMinB = cpt.add(-len,-len);
        
        Line l = new Line();
        l.setStartX(cptMajT.getX());
        l.setStartY(cptMajT.getY());
        l.setEndX(cptMajB.getX());
        l.setEndY(cptMajB.getY());
        l.setStroke(col);
        l.setStrokeWidth(2);
        
        ancDrawPlayersHere.getChildren().add(l);
        
        l = new Line();
        l.setStartX(cptMinT.getX());
        l.setStartY(cptMinT.getY());
        l.setEndX(cptMinB.getX());
        l.setEndY(cptMinB.getY());
        l.setStroke(col);
        l.setStrokeWidth(2);
        
        ancDrawPlayersHere.getChildren().add(l);
        
        TranslateTransition x = new TranslateTransition(Duration.millis(durationMili/10));
        x.setFromX(787);
        x.setFromX(264);
        
        x.setToX(pointTo.getX());
        x.setToY(pointTo.getY());
        
        ancDrawCardsAnim.getChildren().add(toAdd);
        toAdd.setTranslateX(787);
        toAdd.setTranslateX(264);
        
        x.setOnFinished(e -> {
            vb.getChildren().add(toAdd);
        });
        
        transitionCards.add(x);
        
    }
    
    /**
     * Zero based.
     * @param plNum
     * @param card 
     */
    public void addCard(int plNum, City card) {
        TitledPane tp; VBox vb;
        Color col;
        switch (plNum) {
            case 0:
                tp = pl1Title;
                vb = pl1Cards;
                col = Color.BLACK;
                break;
            case 1:
                tp = pl2Title;
                vb = pl2Cards;
                col = Color.BLUE;
                break;
            case 2:
                tp = pl3Title;
                vb = pl3Cards;
                col = Color.GREEN;
                break;
            case 3:
                tp = pl4Title;
                vb = pl4Cards;
                col = Color.RED;
                break;
            case 4:
                tp = pl5Title;
                vb = pl5Cards;
                col = Color.WHITE;
                break;
            case 5:
                tp = pl6Title;
                vb = pl6Cards;
                col = Color.YELLOW;
                break;
            default:
                return;
        }
        plCardsAcc.setExpandedPane(tp);
        // Add imageview to vbox, getX, Y loc, setVisible False
        // remove from there
        // add to root anchorpane
        ImageView toAdd = new ImageView(juiHelper.loadCard(card.getId()));
        toAdd.setId("-1");
        toAdd.setFitWidth(200);
        toAdd.setPreserveRatio(true);
        vb.getChildren().add(toAdd);
        toAdd.setOnMouseClicked(ev -> {
            Point2D locPl = card.getCoord();
            double y = gameboardImg.getImage().getHeight();
            double x = gameboardImg.getImage().getWidth();
            scp.setHvalue(locPl.getX() / x);
            scp.setVvalue(locPl.getY() / y);
            if (toAdd.getId().equals("1")) {
                toAdd.setId("0");
                toAdd.setImage(juiHelper.loadCard(card.getId(), false));
            } else {
                toAdd.setId("1");
                // show back
                toAdd.setImage(juiHelper.loadCard(card.getId(), true));
            }
            
        });
        //Add x marks spot
        Point2D cpt = card.getCoord();
        
        final double len = 10;
        
        
        
        Point2D cptMajT = cpt.add(-len,len);
        Point2D cptMajB = cpt.add(len,-len);
        Point2D cptMinT = cpt.add(len,len);
        Point2D cptMinB = cpt.add(-len,-len);
        
        Line l = new Line();
        l.setStartX(cptMajT.getX());
        l.setStartY(cptMajT.getY());
        l.setEndX(cptMajB.getX());
        l.setEndY(cptMajB.getY());
        l.setStroke(col);
        l.setStrokeWidth(2);
        
        ancDrawPlayersHere.getChildren().add(l);
        
        l = new Line();
        l.setStartX(cptMinT.getX());
        l.setStartY(cptMinT.getY());
        l.setEndX(cptMinB.getX());
        l.setEndY(cptMinB.getY());
        l.setStroke(col);
        l.setStrokeWidth(2);
        
        ancDrawPlayersHere.getChildren().add(l);
        
        // Animation time!
        //toAdd.setVisible(false);
//        Point2D end = toAdd.localToScene(0, 0);
//        Point2D start = new Point2D(300, 300);
//        
//        TranslateTransition moveCd = new TranslateTransition(Duration.millis(durationMili));
//        
//        ancDrawCardsAnim.getChildren().add(toAdd);
//        moveCd.setFromX(start.getX());
//        moveCd.setFromY(start.getY());
//        moveCd.setToX(end.getX());
//        moveCd.setToY(end.getY());
//        moveCd.setOnFinished(e -> {});
//        moveCd.play();
    }

    /**
     * Zero based.
     * @param plNum
     * @param card 
     */
    public void removeCard(int plNum, City card, int originalIndex) {
        
        // Precondition: Cards added in order same as cards in player object.
        VBox box = getVBoxPl(plNum);
        box.getChildren().remove(originalIndex);
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
        
        xoffLoc = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLLOC_XOFF));
        yoffLoc = Integer.parseInt(props.getProperty(JTEPropertyType.IMG_PLLOC_YOFF));
        
        plImgL.setTranslateX(plPos.getX() + xoffLoc);
        plImgL.setTranslateY(plPos.getY() + yoffLoc);
        
        //plImgL.relocate(plPos.getX() + xoff, plPos.getY() + yoff);
        
        // Make plImgL draggable.
        
        plImgL.setOnMouseDragged(ev -> {
            if (!(gsm.getPlayerNum(gsm.getCurrentPlayer()) == num)) {return;}
            Point2D pt = plImgL.localToParent(ev.getX() + xoffLoc, ev.getY() + yoffLoc);
            
            plImgL.setTranslateX(pt.getX());
            plImgL.setTranslateY(pt.getY());
        });

        plImgL.setOnMouseReleased(ev -> {
            if (!(gsm.getPlayerNum(gsm.getCurrentPlayer()) == num)) {return;}
            
            Point2D pt = plImgL.localToParent(ev.getX() + xoffLoc, ev.getY() + yoffLoc);
            Point2D pt2 = pt.subtract(xoffLoc, yoffLoc);
            City foundCity = getGSM().getCityFromCoord(pt2, null);

            if (foundCity == null) {
                plImgL.setTranslateX(getGSM().getCurrentPlayer().getCurrentCity().getCoord().getX() + xoffLoc);
                plImgL.setTranslateY(getGSM().getCurrentPlayer().getCurrentCity().getCoord().getY() + yoffLoc);
                return;
            }

            noAnimatePlayer = true;
            ev = new MouseEvent(null, pt2.getX(), pt2.getY(), 0, 0, null, 0,
                    noAnimatePlayer, noAnimatePlayer, noAnimatePlayer, noAnimatePlayer,
                    noAnimatePlayer, noAnimatePlayer, noAnimatePlayer, noAnimatePlayer, noAnimatePlayer, noAnimatePlayer, null);
            // We only care about x and y.
//            if (eventhdr.gameBoardClick(ev)) {
//                plImgL.setTranslateX(pt.getX());
//                plImgL.setTranslateY(pt.getY());
//            } else {
//                plImgL.setTranslateX(getGSM().getCurrentPlayer().getCurrentCity().getCoord().getX() + xoffLoc);
//                plImgL.setTranslateY(getGSM().getCurrentPlayer().getCurrentCity().getCoord().getY() + yoffLoc);
//            }
        });
        
    }
    
    private boolean noAnimatePlayer;
    private int xoffLoc;
    private int yoffLoc;
    
    
    public void setDice(int diceRoll) {
        Image dice = juiHelper.loadDice(diceRoll);
        imgDice.setImage(dice);
    }
    
    private VBox getVBoxPl(int pl) {
        VBox tp;
        switch (pl) {
            case 0:
                tp = pl1Cards;
                break;
            case 1:
                tp = pl2Cards;
                break;
            case 2:
                tp = pl3Cards;
                break;
            case 3:
                tp = pl4Cards;
                break;
            case 4:
                tp = pl5Cards;
                break;
            case 5:
                tp = pl6Cards;
                break;
            default:
                return null;
        }
        return tp;
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
    
    public void activatePlayer(int pl, boolean firstMove) {
        plCardsAcc.setExpandedPane(getTiledPanePl(pl));
        //int pl = gsm.getPlayerNum(gsm.getCurrentPlayer());    
        ImageView plImg = plloc[pl];
        
        ancDrawPlayersHere.getChildren().remove(plImg);
        ancDrawPlayersHere.getChildren().add(plImg);
        
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
            if (getGSM().isCityNoGood(c)) {
                continue;
            }
            next = c.getCoord();
            Line l = new Line();
            l.setStartX(home.getX());
            l.setStartY(home.getY());
            l.setEndX(next.getX());
            l.setEndY(next.getY());
            l.setStroke(Color.RED);
            l.setStrokeWidth(2);
            ancDrawStuffHere.getChildren().add(l);
        }
        if (firstMove) {
            for (City c: citySea) {
                if (getGSM().isCityNoGood(c)) {
                    continue;
                }
                next = c.getCoord();
                Line l = new Line();
                l.setStartX(home.getX());
                l.setStartY(home.getY());
                l.setEndX(next.getX());
                l.setEndY(next.getY());
                l.setStroke(Color.BLUE);
                l.setStrokeWidth(2);
                ancDrawStuffHere.getChildren().add(l);
            }
        }
        // Flying
        
        boolean res = !gsm.canFly();
        flyButton.setDisable(res);
        
    }
    
    public void uiWinGame(Player pl) {
        eventhdr.winClick(pl);
        ((Stage) root.getScene().getWindow()).close();
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
        if (!noAnimatePlayer) {
            
            int pl = gsm.getPlayerNum(gsm.getCurrentPlayer());
            
            ImageView plImg = plloc[pl];
            
            Point2D cityNewC = cityNew.getCoord();
            Point2D cityOldC = cityOld.getCoord();
            
            plImg.setTranslateX(cityOldC.getX() + xoffLoc);
            plImg.setTranslateY(cityOldC.getY() + yoffLoc);
            
            
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

            movePlTrans.setOnFinished(e -> {
                ancDrawPlayersHere.setMouseTransparent(false);
                Toolkit.getToolkit().exitNestedEventLoop(0, null);
            });

            ancDrawPlayersHere.setMouseTransparent(true);

            movePlTrans.play();
            Toolkit.getToolkit().enterNestedEventLoop(0);
        }
        noAnimatePlayer = false;
        
    }

    enum UIState {
        NO_CLICK, CLICK_PLAYER;
    }
    
    
    
}
