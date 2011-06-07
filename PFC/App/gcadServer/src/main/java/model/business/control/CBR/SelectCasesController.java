package model.business.control.CBR;

import java.util.ArrayList;
import java.util.List;

import model.business.knowledge.Project;

/***
 * Class used to retrieve a quantity of cases, after evaluation
 */
public class SelectCasesController
{    

	public static List<Project> selectAll(List<CaseEval> cases) {
		List<Project> res = new ArrayList<Project>();
		for(CaseEval ce: cases)
			res.add(ce.getCaseP());
		return res;
    }

    // Selects top K cases
    public static List<Project> selectTopK(List<CaseEval> cases, int k) {
    	List<Project> res = new ArrayList<Project>();
		for(int i=0; i<k; i++)
			res.add(cases.get(i).getCaseP());
		return res;    
    }
    
//    /**
//     * Selects all cases but returns them into RetrievalResult objects
//     * @param cases to select
//     * @return all cases into RetrievalResult objects
//     */
//    public static Collection<RetrievalResult> selectAllRR(Collection<RetrievalResult> cases)
//    {
//	return cases;
//    }
//    
//    /**
//     * Selects top k cases but returns them into RetrievalResult objects
//     * @param cases to select
//     * @return top k cases into RetrievalResult objects
//     */
//    public static Collection<RetrievalResult> selectTopKRR(Collection<RetrievalResult> cases, int k)
//    {
//	ArrayList<RetrievalResult> res = new ArrayList<RetrievalResult>();
//	Iterator<RetrievalResult> cIter  =cases.iterator(); 
//	for(int c=0; c<k && c<cases.size(); c++)
//	    res.add(cIter.next());
//	return res;    
//    }
}
