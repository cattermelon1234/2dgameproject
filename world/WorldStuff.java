package world;

import main.GamePanel;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import main.*;
import entity.Player;

/**
 * world / tiles for the player
 */
public class WorldStuff{
    
    GamePanel gp;
    public Tile[] tile;
    int tileMap[][];
    String tileNames[] = {"gray", "darkbrown", "grass", "sand", "talldarktree", "talltree", "bricks", 
    "locker", "wall", "water"};
    BuildingLoc build1 = new BuildingLoc(8, 12, 6, 11);
    Player curPlayer;

    /** 
    * Constructs a new WordStuff that sets tiles with their images and 
    * sets non-accessible tiles as collidable
    * @param GamePanel gp
    * @param Player p
    */
    public WorldStuff(GamePanel  gp, Player p) {
        this.gp = gp;
        this.curPlayer = p;

        tile = new Tile[10];
        tileMap = new int[gp.colsInWorld][gp.rowsInWorld];

        try{ 
            for (int i = 0; i < tileNames.length; i++) {
                tile[i] = new Tile();
                // System.out.println("/res/tiles/" + tileNames[i] + ".png");
                tile[i].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + tileNames[i] + ".png"));
                
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        tile[4].setCollidable();
        tile[5].setCollidable();
        tile[6].setCollidable();
        tile[7].setCollidable();
        tile[8].setCollidable();
        createWorld("/res/maps/world01.txt");
    }
    
    /**
    * Reads the mapFile, removes all the sapces, and puts each number into an array
    * @param mapFile String
    */
    public void createWorld(String mapFile) {
        
        try{
            InputStream is = getClass().getResourceAsStream(mapFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // reads text file

            int col = 0;
            int row = 0;

            while (col < gp.colsInWorld && row < gp.rowsInWorld) {
                String line = br.readLine();
                
                line = line.replaceAll(" ","");

                while (col < gp.colsInWorld) {
                    
                    String s = line.charAt(col) + "";
                    int num = Integer.parseInt(s);
                    tileMap[col][row] = num;
                    col++;
                }
                if(col == gp.colsInWorld) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch( Exception e) {

        }
    }

    /**
    * Displays the part of the world that is on the player's screen
    * @param Graphics2D g2
    */
    public void display(Graphics2D g2) {

        int worldRow = 0;
        int worldCol = 0;
        int curTileX;
        int curTileY;
        int specialWorldCol;
        int specialWorldRow;

        while (worldCol < gp.colsInWorld && worldRow < gp.rowsInWorld) {
            specialWorldCol = 0;
            specialWorldRow = 0;

            curTileX = worldCol * gp.tileDimension;
            curTileY = worldRow * gp.tileDimension;

            int tileNum = tileMap[worldCol][worldRow];

            int screenX = curTileX - gp.getCurPlayer().playerX + gp.getCurPlayer().screenX;
            int screenY = curTileY - gp.getCurPlayer().playerY + gp.getCurPlayer().screenY;

            if (isWithinScreen(curTileX, curTileY)) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileDimension, gp.tileDimension, null);
                
            }
            worldCol++;

            if(worldCol == gp.colsInWorld) {
                worldCol = 0;
                worldRow++;
            }
            
        }
 
    }

    /**
    * Checks if a certain tile is within the player's range of vision
    * @param curTileX int
    * @param curTileY int
    *
    * @return boolean
    */
    public boolean isWithinScreen(int curTileX, int curTileY) {
        if (curTileX + gp.tileDimension > gp.getCurPlayer().getPlayerX() - gp.getCurPlayer().screenX && 
                curTileX - gp.tileDimension < gp.getCurPlayer().getPlayerX() + gp.getCurPlayer().screenX && 
                curTileY + gp.tileDimension > gp.getCurPlayer().getPlayerY() - gp.getCurPlayer().screenY &&
                curTileY - gp.tileDimension < gp.getCurPlayer().getPlayerY() + gp.getCurPlayer().screenY) {
                    return true;
        }
        return false;
    }

    /**
    * Checks if something is in the building given their location
    * @param col int
    * @param row int
    *
    * @return boolean
    */
    public boolean isInBuilding(int col, int row) {
        if (col >= 8 && col <= 11 && row >= 6 && row <= 11) {
            return true;
        }
        return false;
    }

    /**
    * Return tileMap in number form
    * @return tileMap
    */
    public int[][] getTileMap() {
        return tileMap;
    }
}
