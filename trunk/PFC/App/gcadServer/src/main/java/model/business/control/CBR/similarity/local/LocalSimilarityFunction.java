package model.business.control.CBR.similarity.local;

import exceptions.NoApplicableTypeException;

/**
 * Interface that must be implemented in order to returned the local similarity value.
 */
public interface LocalSimilarityFunction {
	
	 public double getSimilarity(Object value1, Object value2) throws NoApplicableTypeException;

}
