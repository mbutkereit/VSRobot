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
	public MoveGripperStub getGripperStub() {
		return new MoveGripperStub(fifo);
	}
}
