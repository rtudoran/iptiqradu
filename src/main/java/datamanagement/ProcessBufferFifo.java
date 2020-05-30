package datamanagement;

public class ProcessBufferFifo extends ProcessBuffer {

	public ProcessBufferFifo(int capacity) {
		super(capacity);
	}
	
	// Implementing the FIFO policy for the full capacity case 
	@Override
	public boolean add(Process proc) {
	
		if (this.getNumberOfProcesses() == this.capacity) {
			super.removeProcess(buffer.get(0).PID); // remove oldest process 
		}

		return super.add(proc);
	
	}

}
