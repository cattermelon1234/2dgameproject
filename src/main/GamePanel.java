package main;

import entity.*;
import entity.object.MapObject;
import websocket.model.MoveEvent;
import websocket.model.MoveEventDecoder;
import world.*;
import java.util.Scanner;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.websocket.DecodeException;

/**
* 
*/
public class GamePanel extends JPanel implements Runnable {

    public final int colsInWorld = 50;
    public final int rowsInWorld = 50;
    public final int originalTileSize = 16;
    private boolean gameJustBegun = true;
    private Scanner scan = new Scanner(System.in);
    private String name;
    WebClient webClient = new WebClient(this);
    
    final int scale = 3;

    // public final int colsInScreen = 16;
    // public final int rowsInScreen = 12;
    public int colsInScreen = 16;
    public int rowsInScreen = 12;

    public final int tileDimension = originalTileSize * scale;
    final int screenWidth = colsInScreen * tileDimension;
    final int screenHeight = rowsInScreen * tileDimension;
    private final long ms = 1000000000;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    public Player hider = new Player(this, keyH);
    public Player seeker = new Player(this, keyH);
    
    public Player curPlayer;
    public WorldStuff worldM;
    public MapObject objArray[] = new MapObject[30];
    public ObjectSetter oSetter = new ObjectSetter(this);
    public String playerName;

    /**
    * Constructs a new GamePanel
    * @param n the current string
    */
    public GamePanel(String n) {
        
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        webClient = new WebClient(this);
        webClient.start();
        this.playerName = n;
        if (playerName.equals("hider")) {
            curPlayer = hider;
        }
        else {
            curPlayer = seeker;
            
        }
        seeker.setSeekerImage();
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        worldM = new WorldStuff(this, getCurPlayer());
        seeker.playerX = 41 * tileDimension;
        seeker.playerY = 6 * tileDimension;
        hider.playerX = 5 * tileDimension;
        seeker.playerX = 47 * tileDimension;
    }

    /**
    * Creates a new gameThread
    */
    public void startGameThread() {
        gameThread = new Thread(this);
    }

    /**
    * The gameloop
    */
    public void run() {

        long drawInterval = ms / 60;
        long timeUntilDraw = System.nanoTime() + drawInterval; // next time it draws
        while (gameThread != null) {
            repaint();
            if (hider.playerX / tileDimension == seeker.playerX / tileDimension && hider.playerY / tileDimension == seeker.playerY / tileDimension) {
                System.out.println("game over, seeker wins!");
                System.exit(0);
            }
            
            else if (hider.playerX / tileDimension == 47 && hider.playerY / tileDimension == 11 || hider.playerX / tileDimension == 48 && hider.playerY / tileDimension ==  35) {
                System.out.println("game over, hider wins!");
                System.exit(0);
            }
            try {
                long timeLeft = timeUntilDraw - System.nanoTime(); // remaining time until next draw
                timeLeft /= ms;

                if (timeLeft < 0) {
                    timeLeft = 0;
                }
                Thread.sleep(timeLeft);
                timeUntilDraw += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * 
    */
    public void gameBuild() {
        oSetter.setObject();
    }

    /**
    * 
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // changes g2 graphics to a 2d graphic with more functions
        worldM.display(g2);

        for (int i = 0; i < objArray.length; i++) {
            if (objArray[i] != null) {
                objArray[i].draw(g2, this);
            }
        }

        hider.paint(g2);
        seeker.paint(g2);
        g2.dispose(); // saves memory and disposes of the old stored info in the g2

    }

    /**
    * If any key is pressed, the boolean for that key is changed to true and the String direction 
    * is set to the currently direction
    * @param keyH the keyHandler
    */
    public void handleKeyEvent(KeyHandler keyH) {
        MoveEvent event = new MoveEvent();
        event.setPlayerName(playerName);
        event.setMove("");
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) {
            String direction = "";
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            event.setMove(direction);
        }
        webClient.send(event);
    }

    /**
     * updates the player on the other
     * player's movement
     * @param message the message sent with move directions
     */
    public void updatePlayer(String message) {
        MoveEvent event;
        try {
            event = (new MoveEventDecoder()).decode(message);
        } catch (DecodeException e) {
            e.printStackTrace();
            return;
        }
        if (event.getPlayerName().equals("hider")) {
            hider.updateDirection(event);
        }
        else {
            seeker.updateDirection(event);
        }
   
    }

    /**
     * gets screen width
     * @return screen width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * gets screen height
     * @return the screen's height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * gets the name of the player
     * @return name of cur player
     */
    public String getName() {
        return name;
    }

    /**
     * gets the current player
     * @return current player
     */
    public Player getCurPlayer() {
        return curPlayer;
    }
}
