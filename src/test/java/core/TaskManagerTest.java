package core;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import datamanagement.Process;
import util.Config;
import util.Config.ListPolicy;
import util.UtilTestFunctions;

public class TaskManagerTest extends UtilTestFunctions {

	/*
	 * Testing for adding functionality based on all 3 policies 
	 */
	@Test
	public void testAddProcess() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));
		taskManager.add(new Process(3, 2));

		List<Process> processes = taskManager.list();

		assertTrue(processes.size() == 3);
		
		int[] expected = new int[] {0, 1, 2};
		
		assertTrue(checkOrder(expected, processes));
		
	}
	
	@Test
	public void testAddProcessBase() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(0, 3);
		taskManager.add(1, 3);
		taskManager.add(2, 2);
		taskManager.add(3, 2);

		List<Process> processes = taskManager.list();

		assertTrue(processes.size() == 3);
		
		int[] expected = new int[] {0, 1, 2};
		
		assertTrue(checkOrder(expected, processes));
		
	}
	
	@Test
	public void testAddProcessFifo() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy("Fifo"); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));
		taskManager.add(new Process(3, 2));

		List<Process> processes = taskManager.list();

		assertTrue(processes.size() == 3);
		
		int[] expected = new int[] {1, 2, 3};
		
		assertTrue(checkOrder(expected, processes));
		
	}
	
	@Test
	public void testAddProcessPriority() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy("Priority"); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));
		taskManager.add(new Process(3, 2));
		taskManager.add(new Process(4, 4));

		List<Process> processes = taskManager.list();

		assertTrue(processes.size() == 3);
		
		int[] expected = new int[] {0, 1, 4};
		
		assertTrue(checkOrder(expected, processes));
		
	}
	
	/*
	 * Tests for kill functionality
	 */
	
	@Test
	public void testKillProcess() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));

		taskManager.kill(1);
		List<Process> processes = taskManager.list();
		
		assertTrue(processes.size() == 2);
		
	}
	
	@Test
	public void testKillPriority() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));

		taskManager.killGroup(3);
		List<Process> processes = taskManager.list();
		
		assertTrue(processes.size() == 1);
		
	}
	
	@Test
	public void testKillAll() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));

		taskManager.killAll();
		List<Process> processes = taskManager.list();
		
		assertTrue(processes.size() == 0);
		
	}
	
	/*
	 * Tests for kill functionality
	 */
	
	@Test
	public void testListByPriority() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
		
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(0, 3));
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 2));
			
		List<Process> sortList = taskManager.list("PRIORITY");
		int[] expected = new int[] {2, 0, 1};
			
		assertTrue(checkOrder(expected, sortList));
	}
		
	@Test
	public void testListByPid() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
			
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 3));
		taskManager.add(new Process(0, 2));
			
		List<Process> sortList = taskManager.list("PID");
		int[] expected = new int[] {0, 1, 2};
			
		assertTrue(checkOrder(expected, sortList));
	}
	
	@Test
	public void testList() {
		Config config = new Config();
		try {
			config.setAndValidateCapacity("3");
			config.setAndValidateAddPolicy(null); // default policy
		} catch (ParseException e) {
			assertTrue(false);
		}
			
		TaskManager taskManager = new TaskManager(config);
		taskManager.add(new Process(1, 3));
		taskManager.add(new Process(2, 3));
		taskManager.add(new Process(0, 2));
			
		List<Process> sortList = taskManager.list(null);
		int[] expected = new int[] {1, 2, 0};
			
		assertTrue(checkOrder(expected, sortList));
	}
}
