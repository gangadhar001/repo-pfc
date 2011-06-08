package model.business.control.CBR.similarity.global;

/**
 * Interface that must be implemented in order to returned the global similarity value.
 */
public interface GlobalSimilarityFunction {    

	public double computeGlobalSimilarity(double[] values, double[] weigths, int numberOfAttributes);
	
}
