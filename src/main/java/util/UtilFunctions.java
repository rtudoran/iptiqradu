package util;

import java.util.Comparator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import datamanagement.Process;
import util.Config.ListPolicy;

public class UtilFunctions {

	// Method parses input according to the run options. 
		public static Config parseInput(String[] args) {
			
			Options options = new Options();

		    Option input = new Option("c", "capacity", true, "capacity of task manager in number of processes");
		    input.setType(Number.class);
		    input.setRequired(true);
		    options.addOption(input);

		    Option output = new Option("p", "policy", false, "output file");
		    output.setRequired(false);
		    options.addOption(output);
			
		    CommandLineParser parser = new DefaultParser();
	        HelpFormatter formatter = new HelpFormatter();
	        CommandLine cmd = null;

	        try {
	            cmd = parser.parse(options, args);
	        } catch (ParseException e) {
	            System.out.println(e.getMessage());
	            formatter.printHelp("utility-name", options);
	            System.exit(1);
	        }
	        
	        Config config = new Config();
	        
	        try {
	        	config.setAndValidateCapacity(cmd.getOptionValue("capacity"));
	        	config.setAndValidateAddPolicy(cmd.getOptionValue("policy"));
	        } catch (ParseException e) {
	            System.out.println(e.getMessage());
	            formatter.printHelp("utility-name", options);
	            System.exit(1);
	        }

	        return config;
		}
		
		public static Comparator<Process> createComparator(ListPolicy policy) {
			if (policy == ListPolicy.PID) {
				return new Comparator<Process>() {
					public int compare(Process p1, Process p2) {
						return Integer.compare(p1.PID, p2.PID);
					}
				};
			} else if (policy == ListPolicy.PRIORITY) {
				return new Comparator<Process>() {
					public int compare(Process p1, Process p2) {
						return Integer.compare(p1.priority, p2.priority);
					}
				};	
			} 
			return new Comparator<Process>() {
				public int compare(Process p1, Process p2) {
					return Long.compare(p1.startTime, p2.startTime);
				}
			};	
		}
		
}
