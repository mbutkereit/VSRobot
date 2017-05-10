package client;

/**
 * Die Klasse ist eine Factory f�r Stubs.
 * 
 * @author wilhelm
 *
 */
public class StubFactory {

	/**
	 * Queue f�r die Stubs.
	 */
	private FifoQueue fifo = null;

	public StubFactory(FifoQueue fifo) {
		this.fifo = fifo;
	}

	/**
	 * Holt den richtigen GripperStub
	 * 
	 * @param methode
	 * @return MoveGripperStub
	 */
	public InterfaceIDLCaDSEV3RMIMoveGripper getGripperStub() {
		return new IDLCaDSEV3RMIMoveGripperStub(fifo);
	}
	
	/**
	 * Holt den richtigen VerticalStub
	 * 
	 * @param methode
	 * @return MoveGripperStub
	 */
	public IIDLCaDSEV3RMIMoveVerticalStub getVerticalStub() {
		return new IIDLCaDSEV3RMIMoveVerticalStub(fifo);
	}
	
	/**
	 * Holt den richtigen VerticalStub
	 * 
	 * @param methode
	 * @return MoveGripperStub
	 */
	public IIDLCaDSEV3RMIMoveHorizontalStub getHorizontalStub() {
		return new IIDLCaDSEV3RMIMoveHorizontalStub(fifo);
	}
	
	/**
	 * Holt den richtigen UltraSonicStub
	 * 
	 * @param methode
	 * @return MoveGripperStub
	 */
	public InterfaceIIDLCaDSEV3RMIUltraSonic getUltraSonicStub() {
		return new IIDLCaDSEV3RMIUltraSonicStub(fifo);
	}
}
