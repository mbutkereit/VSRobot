package tests;

import static org.junit.Assert.assertTrue;

import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consumer.FifoQueue;
import consumer.KommunikationsThread;
import stubs.IDLCaDSEV3RMIMoveGripperStub;

public class Testfall1 {

	FifoQueue recieveQueue;

	@Before
	public void setUp() throws Exception {
		KommunikationsThread.IP_ADRESSE = "127.0.0.1";
		FifoQueue sendQueue = new FifoQueue();
		recieveQueue = new FifoQueue();
		KommunikationsThread senderThread = new KommunikationsThread(sendQueue,
				recieveQueue);
		senderThread.start();
		IDLCaDSEV3RMIMoveGripperStub stub = new IDLCaDSEV3RMIMoveGripperStub(
				sendQueue);
		stub.openGripper(20);
	}

	@Test
	public void test() throws InterruptedException {
		JsonObject obj = recieveQueue.deque();
		System.out.println(obj.toString());
		assertTrue(true);
	}

	@After
	public void tearDown() throws Exception {

	}
}
