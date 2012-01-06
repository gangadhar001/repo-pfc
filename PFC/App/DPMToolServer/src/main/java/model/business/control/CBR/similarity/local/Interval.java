package model.business.control.CBR.similarity.local;

import java.io.Serializable;

/**
 * Class used to calculate the similarity between numeric attributes
 * This value is the their distance sim(x,y)= 1 - (|x-y|/interval)
 */
public class Interval implements LocalSimilarityFunction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472469148019111979L;
	private double interval;	

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public double getSimilarity (Object value1, Object value2) {
		if ((value1 == null) || (value2 == null))
			return 0;		

		Number num1 = (Number) value1;
		Number num2 = (Number) value2;
		
		double abs = Math.abs(num1.doubleValue() - num2.doubleValue());
		return 1 - (abs / interval);
	}
	

}
