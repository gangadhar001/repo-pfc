package model.business.control.CBR.similarity.global;

/**
 * Class used to calculate the global similarity between two cases, using Euclidean Distance
 */

//REFERENCE: http://gaia.fdi.ucm.es/research/colibri/jcolibri
public class EuclDistance implements GlobalSimilarityFunction {

	// Uses the evaluation and weights for each attribute of the case
	public double computeGlobalSimilarity(double[] values, double[] weigths, int nAttributes)
	{
		double sum = 0;
		for(int i=0; i<nAttributes; i++)
		{
			sum += Math.pow(values[i], 2) * weigths[i];			
		}
		return Math.sqrt(sum);
	}


}
