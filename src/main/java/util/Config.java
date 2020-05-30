package util;

import java.util.Hashtable;

import org.apache.commons.cli.ParseException;

public class Config {

	// the service can be extended to work also in other modes in the future such as REST, grpc...
	public String serviceMode = "console";
	private int capacity;
	private EvictionPolicy policy;
	
	public enum EvictionPolicy {
		OVERFLOW,
		FIFO,
		PRIORITY
	}
	
	public enum ListPolicy {
		TIME,
		PRIORITY,
		PID
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	// Parse and validate the capacity option
	public void setAndValidateCapacity(String capacity) throws ParseException {
		
		String capacityError = "Capacity is a positive integer";
		try {
			int temp = Integer.parseInt(capacity);
			if (temp > 0) {
				this.capacity = temp;
				return ;
			} else {
				throw new ParseException(capacityError);
			}
		} catch (NumberFormatException exp) {
			throw new ParseException(capacityError);
		}
	}
	
	public EvictionPolicy getAddPolicy() {
		return policy;
	}
	
	// Parse and validate the process eviction option
	public void setAndValidateAddPolicy(String policy) throws ParseException {
		
		
		Hashtable<String, EvictionPolicy> policies = new Hashtable<String, EvictionPolicy>();
		policies.put("overflow", EvictionPolicy.OVERFLOW);
		policies.put("fifo", EvictionPolicy.FIFO);
		policies.put("priority", EvictionPolicy.PRIORITY);
		String policyError = "Policy is one of the following options" + policies.keySet();
		
		//default is overflow
		if (policy == null) {
			this.policy = EvictionPolicy.OVERFLOW;
			return ;
		}
		
		EvictionPolicy tempPolicy = policies.get(policy.toLowerCase());
		if (tempPolicy == null) {
			throw new ParseException(policyError);
		}
		this.policy = tempPolicy;
	};
	
	// Parse and validate the process eviction option
	public static ListPolicy parseListPolicy(String policy) {
		
		Hashtable<String, ListPolicy> policies = new Hashtable<String, ListPolicy>();
		policies.put("time", ListPolicy.TIME);
		policies.put("priority", ListPolicy.PRIORITY);
		policies.put("pid", ListPolicy.PID);
		String policyError = "Policy is one of the following options" + policies.keySet();
		
		// default is overflow
		if (policy == null) {
			return ListPolicy.TIME;
		}
				
		ListPolicy tempPolicy = policies.get(policy.toLowerCase());
		if (tempPolicy == null) {
			return ListPolicy.TIME;
		}
		return tempPolicy;
	}

	
}
