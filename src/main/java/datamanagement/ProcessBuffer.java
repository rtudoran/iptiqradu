package datamanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import util.Config.ListPolicy;
import util.UtilFunctions;

/**
 * 
 * @author rtudoran
 *
 *	The class offers a simple buffer with fixed capacity for handling processes.  
 */
public class ProcessBuffer {
	
	int capacity;
	// if we need to check for process unique id we can move to a Hashtable
	LinkedList<Process> buffer; // structure for keeping the processes in a timely order
	
	public ProcessBuffer(int capacity) {
		buffer = new LinkedList<Process>();
		this.capacity = capacity;
	}

	// Method for adding new processes to the buffer
	public boolean add(Process proc) {
		
		// TODO add check for unique ID if it is not guaranteed to have unique IDs 
		// task claims processes have unique IDs, so the check is not added 
		
		if (this.getNumberOfProcesses() == capacity) {
			return false;
		}
		
		// TODO check if the process has unique ID. The task specifies that the processes have unique ID
		// assert(!this.hasProcess(id));
		
		buffer.add(proc);
		return true;
	}

	// Method for removing a processes from the buffer based on id
	public boolean removeProcess(int id) {
		for (int i = 0; i < buffer.size(); i++) {
			if (buffer.get(i).PID == id) {
				buffer.get(i).kill();
				buffer.remove(i);
				return true; // only one process expected with a given id
			}
		}
		return false;
	}
	
	// Method for removing all processes from the buffer
	public boolean removeAll() {
		for (Process proc : buffer) {
			proc.kill();
		}
		buffer.clear();
		return true;
	}
	
	// remove all processes with a given priority
	public boolean removePriority(int priority) {
		boolean result = false;
		for (int i = 0; i < buffer.size();) {
			if (buffer.get(i).priority == priority) {
				buffer.get(i).kill();
				buffer.remove(i);
				result = true; // remove operation was performed
			} else {
				i++;
			}
		}
		return result;
	}
	
	// list processes in the order they arrived
	public List<Process> list() {
		List<Process> result = new ArrayList<Process>();
		for (Process proc : buffer) {
			result.add(proc);
		}
		return result;
	}
	
	// get the number of processes in the buffer
	public int getNumberOfProcesses() {
		return buffer.size();
	}
	
}
