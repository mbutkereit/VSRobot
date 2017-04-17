package Client;

/**
 * Die Klasse ist eine Factory für Stubs.
 * 
 * @author wilhelm
 *
 */
public class StubFactory {

	/**
	 * Queue für die Stubs.
	 */
	private FifoQueue fifo = null;

	public StubFactory(FifoQueue fifo) {
		this.fifo = fifo;
	}

	/**
	 * Holt den richtigen GripperStub
	 * 
	 * @param methode
	 * @return
	 */
	public MoveGripperStub getGripperStub() {
		return new MoveGripperStub(fifo);
	}
}
