package main;

import java.lang.Comparable;

/**
 * Location class, for location of objects
 */
public class Location implements Comparable {
    private int xPos;
    private int yPos;

    /**
     * Constructs Location with x and y coordinates
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Location(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * X value getter method
     * @return int xPos
     */
    public int getX() {
        return xPos;
    }

    /**
     * Y value getter method
     * @return int yPos
     */
    public int getY() {
        return yPos;
    }

    /**
     * compares this with an object if it's a location
     * 
     * @param o object to compare this to
     * @return 0 if true, -1 if false
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Location) {
            Location otherLoc = (Location) o;
            if (otherLoc.getX() == xPos && otherLoc.getY() == yPos) {
                return 0;
            }
        }
        return -1;
    }
}
