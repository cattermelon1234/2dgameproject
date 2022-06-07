package entity.object;

import java.io.IOException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* Class for the speed up powerup, extends MapObject
*/
public class SpeedPotion extends MapObject {
    
    /**
    * Constructs a speed potion
    * @param int x - x coordinate
    * @param int y - y coordinate
    */
    public SpeedPotion(int x, int y) {
        collision = true;
        name = "speed";

        super.setX(x);
        super.setY(y);

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/speed.png"));
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
