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
	
	/**
	 * Konstruktor
	 * @param fifo
	 */
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
	public IDLCaDSEV3RMIMoveVerticalStub getVerticalStub() {
		return new IDLCaDSEV3RMIMoveVerticalStub(fifo);
	}
	
	/**
	 * Holt den richtigen VerticalStub
	 * 
	 * @param methode
	 * @return MoveGripperStub
	 */
	public IDLCaDSEV3RMIMoveHorizontalStub getHorizontalStub() {
		return new IDLCaDSEV3RMIMoveHorizontalStub(fifo);
	}
	
	/**
	 * Holt den richtigen UltraSonicStub
	 * 
	 * @param methode
	 * @return MoveGripperStub
	 */
	public InterfaceIDLCaDSEV3RMIUltraSonic getUltraSonicStub() {
		return new IDLCaDSEV3RMIUltraSonicStub(fifo);
	}
	
	/**
	 * Holt den richtigen NameserverRegStub
	 * 
	 * @param methode
	 * @return 
	 */
	public IDLCaDSEV3RMINameserverRegistrationStub getNameserverRegStub() {
		return new IDLCaDSEV3RMINameserverRegistrationStub(fifo);
	}
}
