/**
* Class used to apply the Nearest Neighbor algorithm
*/
public class NNMethod {

	/* Apply the algorithm over the cases.
	 *  Return the similarity cases */
	public static List<Project> evaluateSimilarity(Project caseToEval, List<Project> cases, ConfigCBR config, int k) throws Exception
	{
		List<Project> similCases = new ArrayList<Project>();
		List<CaseEval> result = new ArrayList<CaseEval>();
		for(Project caseP: cases)
		{
			result.add(new CaseEval(caseP, getEval(caseToEval, caseP, config)));
		}
		// Sort the result
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
		// Global similarity function used in NN Method
		gsf = new Average();
		
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
			if ((lsf = config.getLocalSimilFunction(attCase1)) != null) {
				// Using reflection in order to get the attribute value
				Field attField1 = Project.class.getDeclaredField(attCase1.getName());
				Field attField2 = Project.class.getDeclaredField(attCase2.getName());
				attField1.setAccessible(true);
				attField2.setAccessible(true);
				// Calculate similarity using the local similarity function
				values[i - 2] = lsf.getSimilarity(attField1.get(caseToEval), attField2.get(caseP));
				weights[i - 2] = config.getWeight(attCase1);
				nAttributes++;
			}
		}
		
		// Return the similarity between both cases, applying the global function (average, in this algorithm)
		return gsf.computeGlobalSimilarity(values, weights, nAttributes);
    }
}
