package model.business.control.CBR.similarity.local;

import exceptions.NoApplicableTypeException;

/**
 * Class used to calculate the similarity between numeric attributes
 * This value is the their distance sim(x,y)= 1 - (|x-y|/interval)
 */
public class Interval implements LocalSimilarityFunction {

	private double interval;	

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public double getSimilarity (Object value1, Object valueo2) throws NoApplicableTypeException {
		if ((value1 == null) || (valueo2 == null))
			return 0;
		if (!(value1 instanceof java.lang.Number))
			throw new NoApplicableTypeException(this.getClass(), value1.getClass());
		if (!(valueo2 instanceof java.lang.Number))
			throw new NoApplicableTypeException(this.getClass(), valueo2.getClass());

		Number num1 = (Number) value1;
		Number num2 = (Number) valueo2;
		
		return 1 - ((double) Math.abs(num1.doubleValue() - num2.doubleValue()) / interval);
	}
	

}
