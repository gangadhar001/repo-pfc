package model.business.control.CBR;

import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Project;

/***
 * Class used to retrieve a quantity of cases, after evaluation
 */
public class SelectCasesController {
	
	public static List<Project> selectAll(List<CaseEval> cases) {
		List<Project> res = new ArrayList<Project>();
		for(CaseEval ce: cases)
			res.add(ce.getCaseP());
		return res;
    }

    // Selects top K cases
    public static List<Project> selectTopK(List<CaseEval> cases, int k) {
    	List<Project> res = new ArrayList<Project>();
		for(int i=0; i<k && i<res.size(); i++)
			res.add(cases.get(i).getCaseP());
		return res;    
    }
}
