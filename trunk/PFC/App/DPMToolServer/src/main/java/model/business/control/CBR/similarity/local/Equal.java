package model.business.control.CBR.similarity.local;

import java.io.Serializable;

/**
 * Class used to calculate the similarity between attributes.
 * This value is 1 if both are equals, and 0 otherwise
 */

// REFERENCE: http://gaia.fdi.ucm.es/research/colibri/jcolibri
public class Equal implements LocalSimilarityFunction, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3842621557300137246L;

	public double getSimilarity(Object value1, Object value2) {
        if ((value1 == null) || (value2 == null))
            return 0;
        return value1.equals(value2) ? 1 : 0;
    }
    
}
