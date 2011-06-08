package model.business.control.CBR.similarity.local;

import exceptions.NoApplicableTypeException;

/**
 * Class used to calculate the similarity between enum attributes.
 * This value is the their distance sim(x,y)=|ord(x) - ord(y)|
 */
public class EnumDistance implements LocalSimilarityFunction {

	public double getSimilarity(Object value1, Object value2) throws NoApplicableTypeException {
		if ((value1 == null) || (value2 == null))
			return 0;
		if(!(value1 instanceof Enum))
			// TODO: 
			throw new NoApplicableTypeException(this.getClass(), value1.getClass());
		if(!(value2 instanceof Enum))
			throw new NoApplicableTypeException(this.getClass(), value2.getClass());
		
		Enum<?> enum1 = (Enum<?>)value1;
		Enum<?> enum2 = (Enum<?>)value2;
		
		double size = enum1.getDeclaringClass().getEnumConstants().length;
		double diff = Math.abs(enum1.ordinal() - enum2.ordinal());
		
		return 1 - (diff / size);
	}

}
