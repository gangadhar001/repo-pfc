package model.business.control.CBR.similarity.local;

import java.io.Serializable;
import java.util.Date;

/**
 * Class used to calculate the similarity between two attributes, than returns 1 if the difference
 * is less than a threshold, and 0 otherwise.
 */

// REFERENCE: http://gaia.fdi.ucm.es/research/colibri/jcolibri
public class Threshold implements LocalSimilarityFunction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472469148019111979L;
	private double threshold;	

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getSimilarity (Object value1, Object value2) {
		if ((value1 == null) || (value2 == null))
			return 0;		

		return compare(value1, value2);		
	}

	private double compare(Object value1, Object value2) {
		double dif = 0.0;
		if (value1 instanceof Integer) {
			dif = Math.abs(((Integer) value1).intValue() - ((Integer) value2).intValue());
		}
		else if (value1 instanceof Double) {
			dif = Math.abs(((Double) value1).doubleValue() - ((Double) value2).doubleValue());
		}
		else if (value1 instanceof Date) {
			dif = Math.abs(((Date)value1).getTime() - ((Date)value2).getTime());
		}
		
		if (dif < threshold)
			return 1.0;
		return 0.0;
	}
	

}
