package nameserver.service42;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoWSD;
import fi.iki.elonen.NanoWSD.WebSocketFrame.CloseCode;

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
            user.webSocket = this;
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
                sendFrame(message);
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