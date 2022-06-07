package entity.object;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import main.GamePanel;
import java.awt.Rectangle;
import entity.Player;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* Parent class for all interactable objects on the map
*
*/
public class MapObject {
    public BufferedImage img;
    public int x, y;
    public String name;
    public boolean collision = false;
    public Rectangle objectArea;
    public int originalAreaX;
    public int originalAreaY;
    private GamePanel gp;
    public boolean hasKey = false;

    
    /** 
     * Checks if the object is within the field's range of vision
     *
     * @param curTileX
     * @param curTileY
     * @param gp
     * @return boolean
     */
    public boolean isWithinScreen(int curTileX, int curTileY, GamePanel gp) {
        objectArea = new Rectangle(0, 0, 48, 48);
        originalAreaX = gp.tileDimension;
        originalAreaY = gp.tileDimension;
        this.gp = gp;
        if (curTileX + gp.tileDimension > gp.getCurPlayer().getPlayerX() - gp.getCurPlayer().screenX && 
                curTileX - gp.tileDimension < gp.getCurPlayer().getPlayerX() + gp.getCurPlayer().screenX && 
                curTileY + gp.tileDimension > gp.getCurPlayer().getPlayerY() - gp.getCurPlayer().screenY &&
                curTileY - gp.tileDimension < gp.getCurPlayer().getPlayerY() + gp.getCurPlayer().screenY) {
                    return true;
        }
        return false;
    }

    
    /** 
     * Draws the object onto the map
     *
     * @param g2
     * @param gp
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = x - gp.getCurPlayer().playerX + gp.getCurPlayer().screenX;
        int screenY = y - gp.getCurPlayer().playerY + gp.getCurPlayer().screenY;

        if (isWithinScreen(x, y, gp)) {

            g2.drawImage(img, screenX, screenY, gp.tileDimension, gp.tileDimension, null);
        }
        
    }

    /**
    * A set state method that sets the image to the correct image based on the
    * current state of the object
    */
    public void setState() {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/key.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }

    
    
    /** 
     * Setter method for x coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    
    /** 
     * Setter method for y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    
    /** 
     * Getter method for x coordinate
     * @return int
     */
    public int getX() {
        return x;
    }

    
    /** 
     * Getter method for y coordinate
     * @return int
     */
    public int getY() { 
        return y;
    }

    
    /** 
     * @return boolean
     */
    public boolean isCollidable() {
        return collision;
    }

    
    /** 
     * @param b
     */
    public void setCollidable(boolean b) {
        this.collision = b;
    }

    public void setKey() {
        hasKey = true;
    }
}
