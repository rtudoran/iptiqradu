package datamanagement;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import util.UtilTestFunctions;

public class ProcessBufferPriorityTest extends UtilTestFunctions {

	@Test
	public void testAddOverLimit() {
		
		int capacity = 2;
		ProcessBuffer buffer = new ProcessBufferPriority(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		assertTrue(capacity == buffer.getNumberOfProcesses());
		
		int[] expected = new int[] {1, 2};
		
		List<Process> processes = buffer.list();
		
		assertTrue(checkOrder(expected, processes));
		
	}
	
	@Test
	public void testFailAddOverLimit() {
		
		int capacity = 2;
		ProcessBuffer buffer = new ProcessBufferPriority(capacity);
		
		buffer.add(new Process(0, 2));
		buffer.add(new Process(1, 2));
		buffer.add(new Process(2, 1));
		
		assertTrue(capacity == buffer.getNumberOfProcesses());
		
		int[] expected = new int[] {0, 1};
		
		List<Process> processes = buffer.list();
		
		assertTrue(checkOrder(expected, processes));
		
	}

}
