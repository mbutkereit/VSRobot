package nameserver.service42;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import service.ServiceList;
import fi.iki.elonen.NanoWSD;
import fi.iki.elonen.NanoWSD.WebSocketFrame.CloseCode;
import fi.iki.elonen.NanoWSD.WebSocketFrame.OpCode;

public class DebugWebSocketServer extends NanoWSD {

    /**
     * logger to log to.
     */
    private static final Logger LOG = Logger.getLogger(DebugWebSocketServer.class.getName());
    UserList userList;

    public DebugWebSocketServer(int port, UserList userList) {
        super(port);
        this.userList = userList;
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake) {
    	 System.out.println(handshake.getHeaders());
        return new DebugWebSocket(this, handshake);
    }

    public class DebugWebSocket extends WebSocket {
        User user;
        IHTTPSession httpSession;
        
        public DebugWebSocket(DebugWebSocketServer server, IHTTPSession handshakeRequest) {
            super(handshakeRequest);
            user = new User();
            user.setWebSocket(this);
            userList.addUser(user);
            this.httpSession = handshakeRequest;
        }

        @Override
        protected void onOpen() {
        }

        @Override
        protected void onClose(CloseCode code, String reason, boolean initiatedByRemote) {
     
                System.out.println("C [" + (initiatedByRemote ? "Remote" : "Self") + "] " + (code != null ? code : "UnknownCloseCode[" + code + "]")
                        + (reason != null && !reason.isEmpty() ? ": " + reason : ""));
            
        }

		@Override
		protected void onMessage(WebSocketFrame message) {
			try {
				message.setUnmasked();
				// Handle Message

				JsonObjectBuilder response = Json.createObjectBuilder();
				response.add("Type", "ServiceList");
				byte[] buffer = WebSocketFrame.text2Binary(message
						.getTextPayload());
				try (InputStream is = new ByteArrayInputStream(buffer, 0,
						buffer.length); JsonReader rdr = Json.createReader(is)) {

					JsonObject obj = rdr.readObject();
					
					
					response.add("values", ServiceList.getInstance().createServiceJsonArray());
				
				} catch (Exception e) {

				}
				WebSocketFrame frame = new WebSocketFrame(OpCode.Text, true,
						WebSocketFrame.text2Binary(response.build().toString()));
				sendFrame(frame);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

        @Override
        protected void onPong(WebSocketFrame pong) {
        
                System.out.println("P " + pong);
            
        }

        @Override
        protected void onException(IOException exception) {
            DebugWebSocketServer.LOG.log(Level.SEVERE, "exception occured", exception);
        }

        @Override
        protected void debugFrameReceived(WebSocketFrame frame) {
  
                System.out.println("R " + frame);
            
        }

        @Override
        protected void debugFrameSent(WebSocketFrame frame) {
     
                System.out.println("S " + frame);
       
        }
    }
}