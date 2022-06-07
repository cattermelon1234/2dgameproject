package world;
import java.awt.image.BufferedImage;

/**  
* Tile class, for collisions (checks if it's collidable or not)
*/
public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    /**
    * Returns true if it is collidable , false if not
    * @return boolean
    */
    public boolean isCollidable() {
        return collision;
    }

    /**
    * Sets objects/tiles as colidable
    */
    public void setCollidable() {
        collision = true;
    }
}