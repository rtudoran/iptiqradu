package util;

import java.util.List;

import datamanagement.Process;

public class UtilTestFunctions {
	
	public boolean checkOrder(int[] expectedOrder, List<Process> result) {
		
		if (result.size() != expectedOrder.length) {
			return false;
		}
		
		for (int i = 0; i < expectedOrder.length; i++) {
			if (expectedOrder[i] != result.get(i).PID) {
				return false;
			}
		}
		
		return true;
	}
}
