package websocket.model;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import websocket.model.MoveEvent;
import com.google.gson.Gson;

/**
 * Translate the movement of the player sent from the client to
 * the server into terms that are understandable
 */
public class MoveEventDecoder implements Decoder.Text<MoveEvent> {

    private static Gson gson = new Gson();

    /**
     * decodes string
     * 
     * @param s to decode
     * @return decoded event
     */
    @Override
    public MoveEvent decode(String s) throws DecodeException {
        MoveEvent event = gson.fromJson(s, MoveEvent.class);
        return event;
    }

    /**
     * checks is string can be decoded
     * 
     * @param s to check
     * @return true if string isn't null, false if is
     */
    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    /**
     * Initialization
     * 
     * @param endpointConfig endpoint configuration
     */
    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    /**
     * Closes resources
     */
    @Override
    public void destroy() {
        // Close resources
    }
}
