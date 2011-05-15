package bussiness.control;

import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Operation;

public class OperationsUtilities {

	public static List<String> getSubgroupsId(List<Operation> operations, String groupName) {
		List<String> result = new ArrayList<String>();
		for(Operation o: operations)
			if (o.getGroup().equals(groupName) && !o.getSubgroup().equals(""))
				result.add(o.getSubgroup());
		return result;
	}
	
	public static List<String> getOperationsGroupId(List<Operation> operations, String groupName) {
		List<String> result = new ArrayList<String>();
		for(Operation o: operations)
			if (o.getGroup().equals(groupName))
				result.addAll(o.getOperations());
		return result;
	}

	public static List<String> getAllOperationsId(List<Operation> operations) {
		List<String> result = new ArrayList<String>();
		for(Operation o: operations)
			result.addAll(o.getOperations());
		return result;
	}
}
