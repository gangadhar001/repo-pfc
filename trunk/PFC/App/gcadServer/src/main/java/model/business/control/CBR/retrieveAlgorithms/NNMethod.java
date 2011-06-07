package model.business.control.CBR.retrieveAlgorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.business.control.ProjectController;
import model.business.control.CBR.Attribute;
import model.business.control.CBR.CaseEval;
import model.business.control.CBR.similarity.global.GlobalSimilarityFunction;
import model.business.control.CBR.similarity.global.jcolibri;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.knowledge.Project;

/**
* Class used to apply the Nearest Neighbour algorithm
*/
public class NNMethod {

	
	@SuppressWarnings("unchecked")      
	/* Apply the algorithm over the cases, using the NN Configuration.
	 *  Return the evaluation of each case */
	public static Collection<CaseEval> evaluateSimilarity(Project caseToEval, List<Project> cases, NNConfig config)
	{
		List<CaseEval> result = new ArrayList<CaseEval>();
		for(Project caseP: cases)
		{
			result.add(new CaseEval(caseP, getEval(caseToEval, caseP, config)));
		}
		// Sort the result
		Collections.sort(result);
		
		return result;
	}
	
	  /**
     * Computes the similarities of the sub-attributes of this CaseComponent and calls the computeSimilarity() method with those values.
     * @param componentOfCase compound attribute of the case
     * @param componentOfQuery compound attribute of the query
     * @param _case case being compared
     * @param _query query being compared
     * @param numSimConfig Similarity functions configuration
     * @return a value between [0..1]
     * @see jcolibri.method.retrieve.NNretrieval.similarity.GlobalSimilarityFunction#compute(jcolibri.cbrcore.CaseComponent, jcolibri.cbrcore.CaseComponent, jcolibri.cbrcore.CBRCase, jcolibri.cbrcore.CBRQuery, jcolibri.method.retrieve.NNretrieval.NNConfig)
     */
    public static double getEval(Project caseToEval, Project caseP, NNConfig config) {
    	LocalSimilarityFunction lsf = null;
    	GlobalSimilarityFunction gsf = null;
    	
    	// Take attributes from each case
		List<Attribute> attributesCaseToEval = ProjectController.getAttributesFromProject(caseToEval);
		List<Attribute> attributesCase = ProjectController.getAttributesFromProject(caseP);
		
		// Evaluation for each attribute
		double[] values = new double[attributesCaseToEval.size()];
		// Weights for each attribute
		double[] weights = new double[attributesCaseToEval.size()];

		int i = 0;
		for(i=0; i<attributesCaseToEval.size(); i++)
		{
			Attribute attCase1 = attributesCaseToEval.get(i);
			Attribute attCase2 = attributesCase.get(i);
			
			// Evaluation of the attributes using local similarity function
			if ((lsf = config.getLocalSimilFunction(attCase1)) != null) {
				// TODO: valor del atributo
				values[i] = lsf.compute(attCase1.value, attCase2.value);
				weights[i] = config.getWeight(attCase1);
			}
		}
		
		// Return the similarity applying the global function (average)
		return gsf.computeSimilarity(values, weights, i);
    }
}
