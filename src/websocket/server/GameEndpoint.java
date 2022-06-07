package websocket.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import websocket.model.*;

/**
 * 
 */
@ServerEndpoint(value = "/game/{player_name}", decoders = MoveEventDecoder.class, encoders = MoveEventEncoder.class)
public class GameEndpoint {
    private Session curSession;
    private static final Set<GameEndpoint> endPoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    /**
     * what to do when session is oppened
     * @param session
     * @param player_name
     */
    public void onOpen(Session session, @PathParam("name") String player_name) throws IOException, EncodeException {
        curSession = session;
        // sets the instance session/initializes it
        endPoints.add(this);
        // adds this specific endpoint to the list of endpoints since it has now been
        // opened
        users.put(session.getId(), player_name);
    }

    /**
     * what to do when there is a message
     * @param session
     * @param mEvent
     */
    public void onMessage(Session session, MoveEvent mEvent) throws IOException, EncodeException {
        broadcast(mEvent);
    }

    /**
     * what to do when session is closed
     * @param session
     */
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        endPoints.remove(session);
    }

    /**
     * what to do when there's an error
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // lazy
    }

    /**
     * broadcast even to all clients
     * @param mEvent
     */
    public static void broadcast(MoveEvent mEvent) throws IOException, EncodeException {
        for (GameEndpoint ge : endPoints) {
            try {
                ge.curSession.getBasicRemote().sendObject(mEvent);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
