package entity.object;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* Door object extends MapObject
*/
public class DoorObject extends MapObject{
    String state = "closed";

    /**
    * Constructs a new DoorObject at (x, y)
    *
    * @param x - x coordinate
    * @param y - y coordinate
    */
    public DoorObject(int x, int y) {
        collision = true;
        name = "door";

        super.setX(x);
        super.setY(y);

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/res/object/door.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Sets the image of the door based on the state, there are two possible states:
    * If it is open and if it is closed
    */
    public void setState() {
        try {
            if (state.equals("closed")) {
                img = ImageIO.read(getClass().getResourceAsStream("/res/object/door2.png"));
                state = "open";
            }
            else {
                img = ImageIO.read(getClass().getResourceAsStream("/res/object/door.png"));
                state = "closed";
            }
            
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
