package core;

import java.util.List;

import datamanagement.Process;
import datamanagement.ProcessBuffer;
import datamanagement.ProcessBufferFifo;
import datamanagement.ProcessBufferPriority;
import util.Config;
import util.UtilFunctions;
import util.Config.EvictionPolicy;
import util.Config.ListPolicy;

public class TaskManager implements TaskManagerAPI {
	
	ProcessBuffer handler = null;
	Config config = null;
	
	public TaskManager(Config config) {
		this.config = config;
		createProcessHandler();
	}
	
	public void add(Process proc) {
		handler.add(proc);
		
	}
	
	public void add(int pid, int priority) {
		Process proc = new Process(pid, priority);
		this.add(proc);
		
	}

	public List<Process> list() {
		return handler.list();
	}
	
	public List<Process> list(String policy) {
		ListPolicy option = Config.parseListPolicy(policy);
		List<Process> processes = handler.list();
		processes.sort(UtilFunctions.createComparator(option));
		return processes;
	}

	public void kill(int PID) {
		handler.removeProcess(PID);
		
	}

	public void killGroup(int priority) {
		handler.removePriority(priority);
		
	}

	public void killAll() {
		handler.removeAll();
		
	}
	
	// operational methods
	
	public void createProcessHandler() {
		
		if (EvictionPolicy.FIFO == config.getAddPolicy()) {
			handler = new ProcessBufferFifo(config.getCapacity());
		} else if (EvictionPolicy.PRIORITY == config.getAddPolicy()) {
			handler = new ProcessBufferPriority(config.getCapacity());
		} else {
			handler = new ProcessBuffer(config.getCapacity());
		}
	}

}
