package main;

import websocket.client.GameClientEndpoint;
import websocket.model.MoveEvent;
import websocket.model.MoveEventDecoder;
import websocket.model.MoveEventEncoder;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * WebClient class, uses web socket and sends messages
 */
public class WebClient {
    GamePanel gp;
    GameClientEndpoint clientEndPoint;

    /**
     * Constructs WebClient
     * 
     * @param gp GamePanel WebClient is for
     */
    public WebClient(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Opens web socket and adds listener
     */
    public void start() {
        try {
            // open websocket
            clientEndPoint = new GameClientEndpoint(new URI("ws://192.168.1.25:8080/2dgame/game/brian"));

            // add listener
            clientEndPoint.addMessageHandler(new GameClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {

                    gp.updatePlayer(message);
                }
            });
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }

    /**
     * sends message to web socket
     * 
     * @param event event encoded in the message
     */
    public void send(MoveEvent event) {
        // send message to websocket
        try {
            clientEndPoint.sendMessage((new MoveEventEncoder()).encode(event));
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }
}
