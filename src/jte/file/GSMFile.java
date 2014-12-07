/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jte.JTEPropertyType.DATA_PATH;
import static jte.JTEPropertyType.XML_CITIESFILELOC;
import static jte.JTEPropertyType.XML_CITIESSCH;
import jte.game.City;
import jte.game.GSMBuilder;
import jte.game.GameState;
import jte.game.GameStateManager;
import jte.game.InstructionTypes;
import jte.game.Player;
import jte.game.Restriction;
import properties_manager.PropertiesManager;

/**
 *
 * @author Frank
 */
public class GSMFile {
    
    /**
     * Null if fail.
     * @param filePath
     * @return 
     */
    public static GSMBuilder loadFile(String filePath) throws Exception {
        GSMBuilder gsm;
        
        File fileToOpen = new File(filePath);
        try {
            if (!fileToOpen.exists()) {
                return null;
            }
            fileToOpen.createNewFile();
        } catch (IOException ex) {
            System.err.println("Error reading file.");
            return null;
        }
        
        try {
            byte[] bytes = new byte[Long.valueOf(fileToOpen.length()).intValue()];
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FileInputStream fis = new FileInputStream(fileToOpen);
            
            try (BufferedInputStream bis = new BufferedInputStream(fis)) {
                bis.read(bytes);
            }

            DataInputStream dis = new DataInputStream(bais);

            int lastRoll = dis.readInt();
            String currMessage = dis.readUTF();
            int currPl = dis.readInt();
            int movesLeft = dis.readInt();
            String log = dis.readUTF();
            int nextRedCard = dis.readInt();
            GameState st = GameState.fromInt(dis.readInt());
            int numPlayers = dis.readInt();
            
            // Generate players.
            ArrayList<Player> players = new ArrayList<>(numPlayers);
            
            // Load lookup
            Map<Integer, City> cityToID = null;
            properties_manager.PropertiesManager props = PropertiesManager.getPropertiesManager();
            String schemaPath = props.getProperty(DATA_PATH) + props.getProperty(XML_CITIESSCH);
            CityLoader cityLoader = new CityLoader(schemaPath);

            try {
                cityToID = cityLoader.readCities(props.getProperty(DATA_PATH) + props.getProperty(XML_CITIESFILELOC));
            } catch (IOException ex) {
                Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            // End lookup
            
            for (int i = 0; i < numPlayers; i++) {
                String name = dis.readUTF();
                boolean isCPU = dis.readBoolean();
                
                Player p = new Player(name, isCPU);
                p.setActiveRestriction(new Restriction(InstructionTypes.NOTHING, 0, 0, 0, 0));
                p.setHomeCity(cityToID.get(dis.readInt()));
                p.setCurrentCity(cityToID.get(dis.readInt()));
                
                int ncards = dis.readInt();
                for (int j = 0; j < ncards; j++) {
                    p.addCard(cityToID.get(dis.readInt()));
                }
                
                ncards = dis.readInt();
                for (int j = 0; j < ncards; j++) {
                    p.getCitiesVisited().add(cityToID.get(dis.readInt()));
                }
                
                ncards = dis.readInt();
                for (int j = 0; j < ncards; j++) {
                    p.getRollHistory().add(dis.readInt());
                }
                
                players.add(p);
                
            }
            
            // Done. insert into GSM.
            gsm = new GSMBuilder(st, lastRoll, players, currMessage, currPl, movesLeft, log, nextRedCard, cityToID);
            return gsm;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static void saveFile(String filePath, GameStateManager gsm,
            int numcards, int diceroll, boolean fliedAlready,
            GameState gs, int numPlayers) {
        // buffer what we need from gsm
        String currMessage = gsm.getCurrentMessage();
        if (currMessage == null) currMessage = "";
        int currPl = gsm.getPlayerNum(gsm.getCurrentPlayer());
        int movesLeft = gsm.getMovesLeft();
        String log = gsm.getLog().getText();
        if (log == null) log = "";
        int nextRedCard = gsm.getRedCard().getId();
        int lastRoll = gsm.getRoll();
         
        // Writing
        
        File selectedFile = new File(filePath);
        try {
            selectedFile.createNewFile();
        } catch (IOException ex) {
            System.err.println("Error making file.");
        }
        
        String fileName = selectedFile.getPath();
        FileOutputStream fos;
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(new FileOutputStream(fileName));
            
            dos.writeInt(lastRoll);
            dos.writeUTF(currMessage);
            dos.writeInt(currPl);
            dos.writeInt(movesLeft);
            dos.writeUTF(log);
            dos.writeInt(nextRedCard);
            dos.writeInt(GameState.toInt(gs));
            dos.writeInt(numPlayers);
            
            for(int i = 0; i < numPlayers; i++) {
                Player p = gsm.getPlayerObject(i);
                
                int nCards = p.getCards().size();
                
                dos.writeUTF(p.getName());
                dos.writeBoolean(p.isIsCPU());
                dos.writeInt(p.getHomeCity().getId());
                dos.writeInt(p.getCurrentCity().getId());
                dos.writeInt(nCards);
                
                for (int j = 0; j < nCards; j++) {
                    int cid = p.getCards().get(j).getId();
                    dos.writeInt(cid);
                }

                int nCvisit = p.getCitiesVisited().size();
                dos.writeInt(nCvisit);
                
                for (int j = 0; j < nCvisit; j++) {
                    int cid = p.getCitiesVisited().get(j).getId();
                    dos.writeInt(cid);
                }
                
                dos.writeInt(p.getRollHistory().size());
                for (int j = 0; j < p.getRollHistory().size(); j++) {
                    dos.writeInt(p.getRollHistory().get(j));
                }
            
            }
            
            dos.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
