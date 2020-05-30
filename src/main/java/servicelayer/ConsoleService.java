package servicelayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import core.TaskManagerAPI;
import util.Config;
import util.Config.ListPolicy;
import datamanagement.Process;

public class ConsoleService extends Service {

	private BufferedReader input = null;

	public ConsoleService() {
		this.input = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void runService(TaskManagerAPI handler) {
		System.out.println("Task manager is running. Waiting for commands...");
		try {
			while (true) {
				System.out.println("Next Command:");
				String command = input.readLine();
				Commands cmd = parseCommandAndExecute(command, handler);
				if (cmd == Commands.EXIT) {
					break;
				}
			}
		} catch (IOException e) {
			System.err.println("Input stream error.");
		}
		System.out.println("Task manager finished. Bye bye...");
	}

	private enum Commands {
		ADD, LIST, LISTPOLICY, KILL, KILLGROUP, KILLALL, EXIT, HELP, CONTINUE
	}

	private Commands parseCommandAndExecute(String command, TaskManagerAPI handler) {
		Options options = new Options();

		Option opt = new Option("a", "add", true, "add new process with \"pid\" and \"priority\"");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("l", "list", false, "list processes");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("lp", "listpriority", true, "list processes with policies");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("k", "kill", true, "kill a process based on \"pid\"");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("kg", "killgroup", true, "kill a set of process based on \"priority\"");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("kg", "killall", false, "kill all processes");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("e", "exit", false, "stop task manager");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("h", "help", false, "show menu");
		opt.setRequired(false);
		options.addOption(opt);

		Hashtable<String, Commands> policies = new Hashtable<String, Commands>();
		policies.put("a", Commands.ADD);
		policies.put("l", Commands.LIST);
		policies.put("lp", Commands.LISTPOLICY);
		policies.put("k", Commands.KILL);
		policies.put("kg", Commands.KILLGROUP);
		policies.put("ka", Commands.KILLALL);
		policies.put("e", Commands.EXIT);
		policies.put("h", Commands.HELP);
		String policyError = "Policy is one of the following options" + policies.keySet();

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, new String[] { command });
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);
			return Commands.CONTINUE; // continue with a new command
		}

		Config config = new Config();

		// try {
		Option[] opts = cmd.getOptions();
		if (opts.length == 0) {
			formatter.printHelp("utility-name", options);
			return Commands.CONTINUE; // continue with a new command
		}

		for (int i = 0; i < opts.length; i++) {
			Option o = opts[i];
			String name = o.getOpt();
			List<String> values = o.getValuesList();

			Commands c = policies.get(name);

			if (c == Commands.ADD) {
				int[] intArgs = parseArgsToInt(values);
				if (intArgs == null || intArgs.length != 2) {
					return Commands.CONTINUE;
				}
				handler.add(intArgs[0], intArgs[1]);
			} else if (c == Commands.LIST) {
				List<Process> data = handler.list();
				printResult(data);
			} else if (c == Commands.LISTPOLICY) {
				if (values == null || values.size() == 0) {
					handler.list(null);
				}
				List<Process> data = handler.list(values.get(0));
				printResult(data);
			} else if (c == Commands.KILL) {
				int[] intArgs = parseArgsToInt(values);
				if (intArgs == null || intArgs.length != 1) {
					return Commands.CONTINUE;
				}
				handler.kill(intArgs[0]);
			} else if (c == Commands.KILLGROUP) {
				int[] intArgs = parseArgsToInt(values);
				if (intArgs == null || intArgs.length != 1) {
					return Commands.CONTINUE;
				}
				handler.killGroup(intArgs[0]);
			} else if (c == Commands.KILLALL) {
				handler.killAll();
			} else if (c == Commands.EXIT) {
				return Commands.EXIT;
			} else {
				formatter.printHelp("utility-name", options);
			}

		}

		return Commands.CONTINUE;

	}

	private void printResult(List<Process> data) {
		System.out.println("The following processes are being handled");
		for (Process proc : data) {
			System.out.println(proc);
		}
		System.out.println();
		
	}

	public int[] parseArgsToInt(List<String> values) {
		int[] result = null;

		if (values == null || values.size() != 1) {
			return null;
		}

		String[] args = values.get(0).trim().split(" ");
		result = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			try{
				result[i] = Integer.parseInt(args[i]);
			} catch(NumberFormatException exp) {
				return null;
			}
		}

		return result;
	}

}
