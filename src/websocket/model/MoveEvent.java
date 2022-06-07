package websocket.model;

/**
 * Represents movement of a player
 * Similar to message, except it is movement of the player sent
 * to the other player
 */
public class MoveEvent {
    private String playerName;
    private String move; /* up, down, left, right, "" */

    /**
     * getter for playerName
     * 
     * @return playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * setter for playerName
     * 
     * @param playerName new value for playerName field
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * getter for move
     * 
     * @return move
     */
    public String getMove() {
        return move;
    }

    /**
     * setter for move
     * 
     * @param move new value for move field
     */
    public void setMove(String move) {
        this.move = move;
    }
}
