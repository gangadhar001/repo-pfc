package model.business.knowledge;

import java.io.Serializable;
import java.util.List;

/***
 * This class is used to save information about an operation that is available for a role
 *
 */
public class Operation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3186547952710912418L;
	private String group;
	// In some cases, this value doesn't appear
	private String subgroup;
	private List<String> operations;
	
	public Operation(String group, String subgroup, List<String> operationsName) {
		this.group = group;
		this.subgroup = subgroup;
		this.operations = operationsName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}
	
	

}
