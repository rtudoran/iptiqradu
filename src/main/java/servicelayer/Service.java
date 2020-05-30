package servicelayer;

import core.TaskManagerAPI;
import util.Config;

public abstract class Service {

	public TaskManagerAPI taskManager = null;
	public abstract void runService(TaskManagerAPI handler);
	
	public static Service factory(Config config) {
		if (config.serviceMode.equals("console")) {
			return new ConsoleService();
		}
		
		return null;
	}
}
