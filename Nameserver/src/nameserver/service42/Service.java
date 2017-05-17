package nameserver.service42;

import implementation.IDLCaDSEV3RMINameserverRegistrationImplementation;

import java.io.IOException;

import fi.iki.elonen.NanoWSD;
import provider.Forwarder;
import provider.IDLCaDSEV3RMINameserverRegistrationSkeleton;
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
		IDLCaDSEV3RMINameserverRegistrationImplementation imp = new IDLCaDSEV3RMINameserverRegistrationImplementation();
		imp.registerService("Roboter1.InterfaceIDLCaDSEV3RMIMoveGripper", "127.0.0.1",
				3232);
		imp.registerService("Roboter2.InterfaceIDLCaDSEV3RMIMoveGripper", "127.0.0.1",
				3232);
		imp.registerService("Roboter3.InterfaceIDLCaDSEV3RMIMoveGripper", "127.0.0.1",
				3232);
		
	
		// Forwarding Service
		Thread forwarderProviderThread = new Thread(new Receiver(
				new Forwarder(), 9090));
		forwarderProviderThread.start();
		
		//Registration Service.
		Thread nameserverRegistrationThread = new Thread(new Receiver(
				new IDLCaDSEV3RMINameserverRegistrationSkeleton(
						new IDLCaDSEV3RMINameserverRegistrationImplementation()
						), 
				9091));
		nameserverRegistrationThread.start();

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
