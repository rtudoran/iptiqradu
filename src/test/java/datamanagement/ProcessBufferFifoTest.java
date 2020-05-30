package datamanagement;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import util.UtilTestFunctions;

public class ProcessBufferFifoTest extends UtilTestFunctions {

	@Test
	public void testAddOverLimit() {
		
		int capacity = 2;
		ProcessBuffer buffer = new ProcessBufferFifo(capacity);
		
		buffer.add(new Process(0, 1));
		buffer.add(new Process(1, 1));
		buffer.add(new Process(2, 2));
		
		assertTrue(capacity == buffer.getNumberOfProcesses());
		
		int[] expected = new int[] {1, 2};
		
		List<Process> processes = buffer.list();
		
		assertTrue(checkOrder(expected, processes));
		
	}

}
