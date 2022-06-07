package websocket.client;

import main.GamePanel;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * client for the game server
 */
public class GameClient {

    /**
     * main class
     */
    public static void main(String[] args) {
        try {
            // open websocket
            final GameClientEndpoint clientEndPoint = new GameClientEndpoint(
                    new URI("ws://192.168.1.25:8080/2dgame/game/brian"));

            // add listener
            clientEndPoint.addMessageHandler(new GameClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println("brian: " + message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{'from':'foo','to':'foo', 'content', 'foo'}");

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
