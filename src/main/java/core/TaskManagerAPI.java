package core;

import java.util.List;

import util.Config.ListPolicy;
import datamanagement.Process;

public interface TaskManagerAPI {
	public void add(Process proc);
	public void add(int pid, int priority);
	public List<Process> list(String policy);
	public List<Process> list();
	public void kill(int PID);
	public void killGroup(int priority);
	public void killAll();
}
