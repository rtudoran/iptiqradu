package app;

import org.apache.commons.cli.*;

import core.TaskManager;
import core.TaskManagerAPI;
import servicelayer.Service;
import util.*;

/**
 * 
 * @author rtudoran
 *
 *	The main class to start the task manager service
 * 
 */
public class TaskManagerApp {

	public static void main(String[] args) {
		
		// parse and validate the arguments
		Config config = UtilFunctions.parseInput(args);

		// Run mode is console by default
		Service taskManagerRunner = Service.factory(config);
		TaskManagerAPI handler = new TaskManager(config);
		
		taskManagerRunner.runService(handler);
	}

	
}
