package entity;

import javax.imageio.ImageIO;

import entity.object.KeyObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import main.GamePanel;
import main.KeyHandler;
import websocket.model.MoveEvent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Player class, draws and tracks player (location, direction, collision, etc.)
 */
public class Player {

    private BufferedImage playerImage = null;
    public int playerX, playerY;
    public Rectangle playerArea;
    private int speed;
    public int playerCol, playerRow;
    public static int originalAreaX;
    public static int originalAreaY;
    private boolean isDisguised = false;
    private boolean isSpeed = false;
    private boolean hasKey = false;

    public String direction;
    private BufferedImage up, down, left, right;

    private GamePanel gp;
    private KeyHandler kh;
    public static int screenX;
    public static int screenY;
    private long disguiseLimit;
    private long speedLimit;
    private String name;

    /**
     * Constructs a new player
     * 
     * @param panel game panel
     * @param handler key handler
     */
    public Player(GamePanel panel, KeyHandler handler) {
        gp = panel;
        this.kh = handler;
        playerArea = new Rectangle(8, 16, 32, 32);
        // ScreenX, screenY are the x and y coordinates of the center of the screen
        // so the player is always in the center!
        screenX = gp.getScreenWidth() / 2 - (gp.tileDimension / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.tileDimension / 2);
        playerX = gp.tileDimension * 23;
        playerY = gp.tileDimension * 21;
        originalAreaX = playerArea.x;
        originalAreaY = playerArea.y;
        speed = 10;
        playerCol = playerX / gp.tileDimension;
        playerRow = playerY / gp.tileDimension;
        getPlayerImage();
        direction = "left";
    }

    /**
     * Sets each buffered image with the correct character sprite
     */
    public void getPlayerImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("/res/player/hiderUp.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/res/player/hiderDown.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/player/hiderRight.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/res/player/hiderLeft.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // inherited by child classes:

    /**
     * Speed getter method
     * 
     * @return int speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * sets player speed
     * @param s parameter for speed
     */
    public void setSpeed(int s) {
        speed = s;
    }

    /**
     * Updates the image based on the character's direction and
     * changes the the speed and location if there is no collision
     * @param event is the move event
     */
    public void updateDirection(MoveEvent event) {
        String move = event.getMove();

        if (!event.getMove().equals("")) {
            this.direction = event.getMove();
        }
        if (direction.equals("up")) {
            playerImage = up;
        } else if (direction.equals("down")) {
            playerImage = down;
        } else if (direction.equals("right")) {
            playerImage = right;
        } else if (direction.equals("left")) {
            playerImage = left;
        }

        if (!isCollision()) {
            if (direction.equals("up")) {
                playerY -= speed;
                playerRow = playerY / gp.tileDimension;
            } else if (direction.equals("down")) {
                playerY += speed;
                playerRow = playerY / gp.tileDimension;
            } else if (direction.equals("right")) {
                playerX += speed;
                playerCol = playerX / gp.tileDimension;
            } else if (direction.equals("left")) {
                playerX -= speed;
                playerCol = playerX / gp.tileDimension;
            }
        }
    }

    /**
     * paints player unless they're disguised
     * 
     * @param g2 for drawing player
     */
    public void paint(Graphics2D g2) {

        if (isDisguised == true && System.nanoTime() / 1000000000 - disguiseLimit / 1000000000 > 10) {
            isDisguised = false;
            if (gp.playerName.equals("hider")) {
                getPlayerImage();
            } else {
                setSeekerImage();
            }

            disguiseLimit = 0;
        } else if (isSpeed == true && System.nanoTime() / 1000000000 - speedLimit / 1000000000 > 3) {
            isSpeed = false;
            speed = 10;
            speedLimit = 0;
        }

        if (direction.equals("up")) {
            playerImage = up;
        } else if (direction.equals("down")) {
            playerImage = down;
        } else if (direction.equals("right")) {
            playerImage = right;
        } else if (direction.equals("left")) {
            playerImage = left;
        }

        int objNum = objectCollisionCheck(this);
        if (objNum != 999 && gp.objArray[objNum].name.equals("key")) {
            gp.objArray[objNum] = null;
        } else if (objNum != 999 && gp.objArray[objNum].name.equals("chest")) {
            if (gp.playerName.equals("hider")) {
                if (gp.objArray[objNum].hasKey == true) {
                    gp.objArray[objNum] = null;
                    System.out.println("hasKey!");
                    hasKey = true;
                }
                gp.objArray[objNum] = null;
            }

        } else if (objNum != 999 && gp.objArray[objNum].name.equals("door")) {
            if (this.hasKey) {
                gp.objArray[objNum] = null;
            }
        } else if (objNum != 999 && gp.objArray[objNum].name.equals("speed")) {
            this.speed = 15;
            speedLimit = System.nanoTime();
            gp.objArray[objNum] = null;
            isSpeed = true;
        } else if (objNum != 999 && gp.objArray[objNum].name.equals("disguise")) {
            disguisePlayer();
            System.out.println("disguising");
            disguiseLimit = System.nanoTime();
            gp.objArray[objNum] = null;
            isDisguised = true;
        }

        if (playerImage == null) {
            playerImage = up;
        }

        int screenX = playerX - gp.getCurPlayer().playerX + gp.getCurPlayer().screenX;
        int screenY = playerY - gp.getCurPlayer().playerY + gp.getCurPlayer().screenY;

        if (isWithinScreen(playerX, playerY, gp)) {

            g2.drawImage(playerImage, screenX, screenY, gp.tileDimension, gp.tileDimension, null);
        }

    }

    /**
     * checks if player is within the screen or not to draw
     * tiles
     * @param curTileX is current x pos of tile
     * @param curTileY is current y pos of tile
     * @param gp game panel
     * @return if its within screen or not
     */
    public boolean isWithinScreen(int curTileX, int curTileY, GamePanel gp) {
        if (curTileX + gp.tileDimension > gp.getCurPlayer().getPlayerX() - gp.getCurPlayer().screenX &&
                curTileX - gp.tileDimension < gp.getCurPlayer().getPlayerX() + gp.getCurPlayer().screenX &&
                curTileY + gp.tileDimension > gp.getCurPlayer().getPlayerY() - gp.getCurPlayer().screenY &&
                curTileY - gp.tileDimension < gp.getCurPlayer().getPlayerY() + gp.getCurPlayer().screenY) {
            return true;
        }
        return false;
    }

    /**
     * checks if player is about to collide with somet
     * 
     * @return true if colliding, false if not
     */
    public boolean isCollision() {

        int areaLeftBound = (playerX + playerArea.x);
        int areaRightBound = (playerX + playerArea.x + playerArea.width);
        int areaTopBound = (playerY + playerArea.y);
        int areaBottomBound = (playerY + playerArea.y + playerArea.height);
        int tile1, tile2;

        if (direction.equals("up")) {
            tile1 = gp.worldM.getTileMap()[areaLeftBound / gp.tileDimension][(areaTopBound - speed) / gp.tileDimension];
            tile2 = gp.worldM.getTileMap()[areaRightBound / gp.tileDimension][(areaTopBound - speed)
                    / gp.tileDimension];
            if (gp.worldM.tile[tile1].isCollidable() || gp.worldM.tile[tile2].isCollidable()) {
                return true;
            }
        } else if (direction.equals("down")) {
            tile1 = gp.worldM.getTileMap()[areaLeftBound / gp.tileDimension][(areaBottomBound + speed)
                    / gp.tileDimension];
            tile2 = gp.worldM.getTileMap()[areaRightBound / gp.tileDimension][(areaBottomBound + speed)
                    / gp.tileDimension];
            if (gp.worldM.tile[tile1].isCollidable() || gp.worldM.tile[tile2].isCollidable()) {
                return true;
            }

        } else if (direction.equals("left")) {
            tile1 = gp.worldM.getTileMap()[(areaLeftBound - speed) / gp.tileDimension][areaTopBound / gp.tileDimension];
            tile2 = gp.worldM.getTileMap()[(areaLeftBound - speed) / gp.tileDimension][areaTopBound / gp.tileDimension];
            if (gp.worldM.tile[tile1].isCollidable() || gp.worldM.tile[tile2].isCollidable()) {
                return true;
            }

        } else {
            tile1 = gp.worldM.getTileMap()[(areaRightBound + speed) / gp.tileDimension][areaTopBound
                    / gp.tileDimension];
            tile2 = gp.worldM.getTileMap()[(areaRightBound + speed) / gp.tileDimension][areaBottomBound
                    / gp.tileDimension];
            if (gp.worldM.tile[tile1].isCollidable() || gp.worldM.tile[tile2].isCollidable()) {
                return true;
            }
        }
        return false;

    }

    /**
     * checks if an object collided or not
     * @param p is the player
     * @return returns the index of object in array
     */
    public int objectCollisionCheck(Player p) {

        int index = 999;

        for (int i = 0; i < gp.objArray.length; i++) {
            if (gp.objArray[i] != null) {

                p.setPlayerAreaX(p.getPlayerX() + p.playerArea.x);
                p.setPlayerAreaY(p.getPlayerY() + p.playerArea.y);

                gp.objArray[i].objectArea.x = gp.objArray[i].x + gp.objArray[i].objectArea.x;
                gp.objArray[i].objectArea.y = gp.objArray[i].y + gp.objArray[i].objectArea.y;

                if (p.playerArea.intersects(gp.objArray[i].objectArea)) {
                    index = i;
                }

                p.playerArea.x = p.originalAreaX;
                p.playerArea.y = p.originalAreaY;
                gp.objArray[i].objectArea.x = gp.objArray[i].originalAreaX;
                gp.objArray[i].objectArea.y = gp.objArray[i].originalAreaY;

            }
        }
        return index;
    }

    /**
     * player x-coordinate getter method
     * 
     * @return int playerX
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * player y-coordinate getter method
     * 
     * @return int playerY
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Player x-coordinate setter method
     * 
     * @param x int to set player's x-coord to
     */
    public void setPlayerX(int x) {
        playerX = x;
    }

    /**
     * Player y-coordinate getter method
     * 
     * @param y int to set player's y-coord to
     */
    public void setPlayerY(int y) {
        playerY = y;
    }

    /**
     * GamePanel getter method
     * 
     * @return game panel
     */
    public GamePanel getGamePanel() {
        return gp;
    }

    /**
     * Original height getter method
     * 
     * @return int originalAreaX
     */
    public int getOriginalAreaX() {
        return originalAreaX;
    }

    /**
     * Original width getter method
     * 
     * @return int originalAreaY
     */
    public int getOriginalAreaY() {
        return originalAreaY;
    }

    /**
     * Setter method for playerArea.x
     * 
     * @param x x coord for playerArea
     */
    public void setPlayerAreaX(int x) {
        playerArea.x = x;
    }

    /**
     * Setter method for playerArea.y
     * 
     * @param y y coord for playerArea
     */
    public void setPlayerAreaY(int y) {
        playerArea.y = y;
    }

    /**
     * disguises player when they acquire the powerup
     */
    public void disguisePlayer() {
        up = null;
        down = null;
        right = null;
        left = null;
    }

    /**
     * returns name
     * 
     * @return game panel's name
     */
    public String getName() {
        return gp.getName();
    }

    /**
     * sets default images (seeker)
     */
    public void setSeekerImage() {
        try {
            // CHANGE THE IMAGES LATER ONCE WE DRAW IT
            up = ImageIO.read(getClass().getResourceAsStream("/res/player/demonicFulkBack.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/res/player/demonicFulkFront.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/res/player/demonicFulkLeft.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/player/demonicFulkRight.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}