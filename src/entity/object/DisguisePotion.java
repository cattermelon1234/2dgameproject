package entity.object;

import java.io.IOException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* Class for the disguise powerup availiable to the players, extends MapObject
*/
public class DisguisePotion extends MapObject {
    
    /**
    * Constructs a disguise potion at (x, y)
    *
    * @param x - x coordinate
    * @param y - y coordinate
    */
    public DisguisePotion(int x, int y) {
        collision = true;
        name = "disguise";

        super.setX(x);
        super.setY(y);

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/disguise.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /** 
     * Getter method for x coordinate
     * @return int
     */
    public int getX() {
        return super.getX();
    }

    
    /** 
     * Getter method for y coordinate
     * @return int
     */
    public int getY() {
        return super.getY();
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
}
