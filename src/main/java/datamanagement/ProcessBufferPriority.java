package datamanagement;

public class ProcessBufferPriority extends ProcessBuffer {

	public ProcessBufferPriority(int capacity) {
		super(capacity);
	}

	@Override
	public boolean add(Process proc) {
		
		if (this.getNumberOfProcesses() == this.capacity) {
			Process candidate = this.getLowestPriorityAndOldestProcess(); // cannot be null
			if (candidate.priority < proc.priority) {
				super.removeProcess(candidate.PID); // remove oldest process
			} else {
				return false;
			}
		}

		return super.add(proc);
	}
	
	private Process getLowestPriorityAndOldestProcess() {
		Process proc = null;
		int priority = Integer.MAX_VALUE;
		
		for (Process pr : buffer) {
			if (pr.priority < priority) {
				priority = pr.priority;
				proc = pr;
			}
		}
		
		return proc;
	}
}
