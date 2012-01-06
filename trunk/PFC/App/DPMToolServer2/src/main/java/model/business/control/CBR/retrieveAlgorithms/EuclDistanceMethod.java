package model.business.control.CBR.retrieveAlgorithms;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.business.control.ProjectController;
import model.business.control.CBR.Attribute;
import model.business.control.CBR.CaseEval;
import model.business.control.CBR.ConfigCBR;
import model.business.control.CBR.SelectCasesController;
import model.business.control.CBR.similarity.global.EuclDistance;
import model.business.control.CBR.similarity.global.GlobalSimilarityFunction;
import model.business.control.CBR.similarity.local.Difference;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.knowledge.Project;

/**
* Class used to apply the Euclidean Distance algorithm
*/
public class EuclDistanceMethod {

	/* Apply the algorithm over the cases.
	 *  Return the similarity cases */
	public static List<CaseEval> evaluateSimilarity(Project caseToEval, List<Project> cases, ConfigCBR config, int k) throws Exception
	{
		List<CaseEval> similCases = new ArrayList<CaseEval>();
		List<CaseEval> result = new ArrayList<CaseEval>();
		for(Project caseP: cases)
		{
			result.add(new CaseEval(caseP, getEval(caseToEval, caseP, config)));
		}
		// Sort the result (higher to lower)
		Collections.sort(result);
		
		if (k == 0)
			similCases = SelectCasesController.selectAll(result);
		else
			similCases = SelectCasesController.selectTopK(result, k);
		
		return similCases;
	}
	
	 /*
     * Get the evaluation for each attribute of the cases to compare
     */
    private static double getEval(Project caseToEval, Project caseP, ConfigCBR config) throws Exception {
    	LocalSimilarityFunction lsf = null;
    	GlobalSimilarityFunction gsf = null;
    	
    	// Take attributes from each case
		List<Attribute> attributesCaseToEval = ProjectController.getAttributesFromProject(caseToEval);
		List<Attribute> attributesCase = ProjectController.getAttributesFromProject(caseP);
		gsf = new EuclDistance();
		
		// Evaluation for each attribute (ignore id and serialVersionUID)
		double[] values = new double[attributesCaseToEval.size() - 2];
		// Weights for each attribute (ignore id and serialVersionUID)
		double[] weights = new double[attributesCaseToEval.size() - 2];

		int nAttributes = 0;	
		for(int i=2; i<attributesCaseToEval.size(); i++)
		{
			Attribute attCase1 = attributesCaseToEval.get(i);
			Attribute attCase2 = attributesCase.get(i);
			
			// Evaluation of the attributes using local similarity function			
			Field attField1 = Project.class.getDeclaredField(attCase1.getName());
			Field attField2 = Project.class.getDeclaredField(attCase2.getName());
			attField1.setAccessible(true);
			attField2.setAccessible(true);
			lsf = new Difference();
			
			values[i - 2] = lsf.getSimilarity(attField1.get(caseToEval), attField2.get(caseP));
			weights[i - 2] = config.getWeight(attCase1);
			nAttributes++;
			
		}
		
		// Return the similarity applying the global function (average)
		return gsf.computeGlobalSimilarity(values, weights, nAttributes);
    }
}
