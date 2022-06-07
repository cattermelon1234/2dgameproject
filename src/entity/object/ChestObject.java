package entity.object;
import java.io.IOException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* Class for the chest object that can hold an answer key, extends MapObject
*/
public class ChestObject extends MapObject{

    /**
    * Constructs a new ChestObject at (x, y)
    *
    * @param x - x coordinate
    * @param y - y coordinate
    */
    public ChestObject(int x, int y) {
        collision = true;
        name = "chest";
        super.setX(x);
        super.setY(y);

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/chest.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Sets the state of the chest
    */
    public void setState() {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/key.png"));
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

    /**
    * Sets the key if it is in this chest
    *
    */
    public void setKey() {
        super.setKey();
    }
}
