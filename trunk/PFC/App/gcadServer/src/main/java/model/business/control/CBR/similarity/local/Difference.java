package model.business.control.CBR.similarity.local;

import java.io.Serializable;
import java.util.Date;

/**
 * Class used to calculate the similarity between attributes.
 * This value is 1 if both are equals, and 0 otherwise
 */
public class Difference implements LocalSimilarityFunction, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 328421087228534407L;

	public double getSimilarity(Object value1, Object value2) {
		double diff = 0;
		if (value1 instanceof String)
			diff = value1.toString().equals(value2.toString()) ? 1 : 0;
		else if (value1 instanceof Integer)
			diff = Integer.parseInt(value1.toString()) - Integer.parseInt(value2.toString());
		else if (value1 instanceof Double)
			diff = Double.parseDouble(value1.toString()) - Double.parseDouble(value2.toString());
		else if (value1 instanceof Date)
			diff = ((Date)value1).getTime() - ((Date)value2).getTime();
		return diff;
    }
    
}
