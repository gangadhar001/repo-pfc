package model.business.control.CBR;

import java.util.ArrayList;
import java.util.List;

/***
 * Class used to retrieve a quantity of cases, after evaluation
 */
public class SelectCasesController {
	
	public static List<CaseEval> selectAll(List<CaseEval> cases) {
		List<CaseEval> res = new ArrayList<CaseEval>();
		for(CaseEval ce: cases)
			res.add(ce);
		return res;
    }

    // Selects top K cases
    public static List<CaseEval> selectTopK(List<CaseEval> cases, int k) {
    	List<CaseEval> res = new ArrayList<CaseEval>();
		for(int i=0; i<k && i<cases.size(); i++)
			res.add(cases.get(i));
		return res;    
    }
}
