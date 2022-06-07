package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import entity.*;
import entity.object.ChestObject;
import entity.object.DisguisePotion;
import entity.object.DoorObject;
import entity.object.KeyObject;
import entity.object.SpeedPotion;
import java.util.Random;

/**
 * ObjectSetter class, holds list of objects
 */
public class ObjectSetter {

    GamePanel gp;
    ArrayList<Location> objLocs = new ArrayList<Location>();
    TreeMap<Location, String> objListMap = new TreeMap<Location, String>();

    /**
     * Constructs ObjectSetter
     * 
     * @param gp GamePanel objects are for
     */
    public ObjectSetter(GamePanel gp) {
        this.gp = gp;
        objListMap.put(new Location(25 * gp.tileDimension, 21 * gp.tileDimension), "disguise");
        objListMap.put(new Location(42 * gp.tileDimension, 12 * gp.tileDimension), "door");
        objListMap.put(new Location(45 * gp.tileDimension, 36 * gp.tileDimension), "door");
        objListMap.put(new Location(28 * gp.tileDimension, 25 * gp.tileDimension), "speed");
        objListMap.put(new Location(47 * gp.tileDimension, 8 * gp.tileDimension), "chest");
        objListMap.put(new Location(46 * gp.tileDimension, 2 * gp.tileDimension), "chest");
        objListMap.put(new Location(38 * gp.tileDimension, 2 * gp.tileDimension), "chest");
        // objListMap.put(new Location(47 * gp.tileDimension, 12 * gp.tileDimension),
        // "chest");
        objListMap.put(new Location(47 * gp.tileDimension, 17 * gp.tileDimension), "chest");
        objListMap.put(new Location(47 * gp.tileDimension, 23 * gp.tileDimension), "chest");
        objListMap.put(new Location(47 * gp.tileDimension, 27 * gp.tileDimension), "chest");
        objListMap.put(new Location(41 * gp.tileDimension, 40 * gp.tileDimension), "chest");
        objListMap.put(new Location(33 * gp.tileDimension, 40 * gp.tileDimension), "chest");
        objListMap.put(new Location(25 * gp.tileDimension, 40 * gp.tileDimension), "chest");

    }

    /**
     * puts objects into array of objects
     */
    public void setObject() {
        int count = 0;
        Random rand = new Random();
        int randomNum = (int) (Math.random() * 10);
        for (Map.Entry<Location, String> entry : objListMap.entrySet()) {
            if (entry.getValue().equals("key")) {
                gp.objArray[count] = new KeyObject(entry.getKey().getX(), entry.getKey().getY());
                count++;
            } else if (entry.getValue().equals("chest")) {
                gp.objArray[count] = new ChestObject(entry.getKey().getX(), entry.getKey().getY());
                count++;
            } else if (entry.getValue().equals("door")) {
                gp.objArray[count] = new DoorObject(entry.getKey().getX(), entry.getKey().getY());
                count++;
            } else if (entry.getValue().equals("speed")) {
                gp.objArray[count] = new SpeedPotion(entry.getKey().getX(), entry.getKey().getY());
                count++;
            } else if (entry.getValue().equals("disguise")) {
                gp.objArray[count] = new DisguisePotion(entry.getKey().getX(), entry.getKey().getY());
                count++;
            }

        }
        gp.objArray[randomNum].setKey();
    }
}
