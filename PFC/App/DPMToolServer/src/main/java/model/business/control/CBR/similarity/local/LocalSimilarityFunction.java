package model.business.control.CBR.similarity.local;

/**
 * Interface that must be implemented in order to returned the local similarity value.
 */
public interface LocalSimilarityFunction {
	
	 public double getSimilarity(Object value1, Object value2);

}
