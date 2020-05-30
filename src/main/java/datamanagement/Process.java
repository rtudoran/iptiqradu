package datamanagement;

public class Process {
	public int priority;
	public int PID;
	public long startTime;
	
	public Process(int PID, int priority) {
		this.PID = PID;
		this.priority = priority;
		this.startTime = System.currentTimeMillis();
	}
	
	public void kill() {
		// place holder method to stop and deconstruct the process 
	}
	
	@Override
	public String toString() {
		return "Process with PID = " + this.PID;
	}
}
