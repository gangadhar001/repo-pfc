package model.business.control.CBR.similarity.local;

import java.io.Serializable;
import java.util.Date;

/**
 * Class used to calculate the similarity between attributes.
 * This value is the difference between both attributes
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
		else if (value1 instanceof Integer) {
			diff = Math.abs(Integer.parseInt(value1.toString()) - Integer.parseInt(value2.toString()));
			// Normalize result in [0,1]
			diff = diff / (Math.max(Integer.parseInt(value1.toString()), Integer.parseInt(value2.toString())));
		}
		else if (value1 instanceof Double) {
			diff = Math.abs(Double.parseDouble(value1.toString()) - Double.parseDouble(value2.toString()));
			// Normalize result in [0,1]
			diff = diff / (Math.max(Double.parseDouble(value1.toString()), Double.parseDouble(value2.toString())));
		}
		else if (value1 instanceof Date) {
			diff = Math.abs(((Date)value1).getTime() - ((Date)value2).getTime());
			// Normalize result in [0,1]
			diff = diff / (Math.max(((Date)value1).getTime(), ((Date)value2).getTime()));
		}
	
		return diff;
    }
    
}
