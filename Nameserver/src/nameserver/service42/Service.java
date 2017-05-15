package nameserver.service42;

import java.io.IOException;

import fi.iki.elonen.NanoWSD;

public class Service {

    public static void main(String[] args) throws IOException {
    	UserList list = new UserList();
        NanoWSD ws = new DebugWebSocketServer( 9090, list );
        ws.start();
        LebensChecker a = new LebensChecker(list);
        Thread t = new Thread(a);
        t.start();
        System.out.println("Server started, hit Enter to stop.\n");
        try {
            System.in.read();
        } catch (IOException ignored) {
        }
        ws.stop();
        System.out.println("Server stopped.\n");
    }

    
}
