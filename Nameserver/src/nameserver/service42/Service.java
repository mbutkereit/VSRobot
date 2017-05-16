package nameserver.service42;

import implementation.NameserverRpcImplementation;

import java.io.IOException;


//import fi.iki.elonen.NanoWSD;
import provider.Forwarder;
import provider.Receiver;

public class Service {

    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	 NameserverRpcImplementation imp = new NameserverRpcImplementation();
    	 imp.registerService("InterfaceIDLCaDSEV3RMIMoveGripper", "127.0.0.1", 3232);
		Thread forwarderProviderThread = new Thread(new Receiver(new Forwarder(),9090));
		forwarderProviderThread.start();
		forwarderProviderThread.join();
    	
    /*	UserList list = new UserList();
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
        System.out.println("Server stopped.\n");*/
    }

    
}
