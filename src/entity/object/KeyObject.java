package entity.object;

import java.io.IOException;
import java.io.IOException;
import javax.imageio.ImageIO;

/** 
* Class for the answer key that the hider is trying to find, extends MapObject
*/
public class KeyObject extends MapObject {
    
    /**
    * Constructs a KeyObject at (x, y)
    * 
    * @param x - x coordinate
    * @param y - y coordinate
    */
    public KeyObject(int x, int y) {
        collision = true;
        name = "key";

        super.setX(x);
        super.setY(y);

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/key.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /** 
     * Getter method for x coordinate
     * @return int x
     */
    public int getX() {
        return super.getX();
    }

    
    /** 
     * Getter method for y coordinate
     * @return int y
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
