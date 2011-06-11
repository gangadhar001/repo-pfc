package model.business.control.CBR.similarity.global;

/**
 * Class used to calculate the global similarity between two cases
 */
public class Average implements GlobalSimilarityFunction {

	// Uses the evaluation and weights for each attribute of the case
	public double computeGlobalSimilarity(double[] values, double[] weigths, int nAttributes)
	{
		double sum = 0;
		double totalWeight = 0;
		for(int i=0; i<nAttributes; i++)
		{
			sum += values[i] * weigths[i];
			totalWeight += weigths[i];
		}
		return sum/totalWeight;
	}


}