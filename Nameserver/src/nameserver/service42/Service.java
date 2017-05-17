package nameserver.service42;

import implementation.NameserverRpcImplementation;

import java.io.IOException;

import fi.iki.elonen.NanoWSD;
import provider.Forwarder;
import provider.Receiver;

/**
 * Programmeinstieg
 * @author Wilhelm und Marvin
 *
 */
public class Service {

	/**
	 * Main
	 * @param args Kommandozeilenparameter
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {

		//Test NameserverRpc implementation
		NameserverRpcImplementation imp = new NameserverRpcImplementation();
		imp.registerService("InterfaceIDLCaDSEV3RMIMoveGripper", "127.0.0.1",
				3232);
		
		// Forwarding Service
		Thread forwarderProviderThread = new Thread(new Receiver(
				new Forwarder(), 9090));
		forwarderProviderThread.start();

		UserList list = UserList.getInstance();
		NanoWSD ws = new DebugWebSocketServer(8042, list);
		ws.start();
		LebensChecker life = new LebensChecker(list);
		Thread threadLife = new Thread(life);
		threadLife.start();
		System.out.println("Server started, hit Enter to stop.\n");
		try {
			System.in.read();
			imp.registerService("InterfaceIDLCaDSEV3RMIChristel", "127.0.0.1",
					3232);
		} catch (IOException ignored) {
		}
		
		forwarderProviderThread.join();
		ws.stop();
		System.out.println("Server stopped.\n");
	}

}
