package websocket.model;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import websocket.model.MoveEvent;
import com.google.gson.Gson;

/**
 * Encode the movement of the player from server to client 
 * so it can be sent 
 */
public class MoveEventEncoder implements Encoder.Text<MoveEvent> {

    private static Gson gson = new Gson();

    /**
     * Encodes message
     * 
     * @param message message to encode
     * @return encoded string
     */
    @Override
    public String encode(MoveEvent message) throws EncodeException {
        String json = gson.toJson(message);
        return json;
    }

    /**
     * Initializes
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
