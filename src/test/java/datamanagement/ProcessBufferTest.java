package datamanagement;

import static org.junit.Assert.*;


import java.util.List;

import org.junit.Test;

import datamanagement.Process;
import datamanagement.ProcessBuffer;
import util.UtilTestFunctions;

public class ProcessBufferTest extends UtilTestFunctions {

	@Test
	public void testAddOverLimit() {
		
		int capacity = 2;
		ProcessBuffer buffer = new ProcessBuffer(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		assertTrue(capacity == buffer.getNumberOfProcesses());
		
	}
	
	@Test
	public void testRemoveOnId() {
		int capacity = 3;
		ProcessBuffer buffer = new ProcessBuffer(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		buffer.removeProcess(1);
		buffer.removeProcess(3);
		
		// expected 2 processes remaining
		assertTrue((capacity - 1) == buffer.getNumberOfProcesses());
	}
	
	@Test
	public void testRemoveOnPriority() {
		int capacity = 3;
		ProcessBuffer buffer = new ProcessBuffer(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		buffer.removePriority(1);
		buffer.removePriority(3);
		
		// expected 1 process remaining
		assertTrue(1 == buffer.getNumberOfProcesses());
	}
	
	@Test
	public void testRemoveAll() {
		int capacity = 3;
		ProcessBuffer buffer = new ProcessBuffer(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		buffer.removeAll();
				
		// expected 0 process remaining
		assertTrue(0 == buffer.getNumberOfProcesses());
	}
	
	@Test
	public void testListByTime() {
		int capacity = 3;
		ProcessBuffer buffer = new ProcessBuffer(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		List<Process> sortList = buffer.list();
		int[] expected = new int[] {0, 1, 2};
		
		assertTrue(checkOrder(expected, sortList));
	}
	
}
