package nameserver.service42;
import fi.iki.elonen.NanoWSD.WebSocket;
public class User {
	
	    public WebSocket getWebSocket() {
		return webSocket;
	}

	public void setWebSocket(WebSocket webSocket) {
		this.webSocket = webSocket;
	}

		private WebSocket webSocket;
}
