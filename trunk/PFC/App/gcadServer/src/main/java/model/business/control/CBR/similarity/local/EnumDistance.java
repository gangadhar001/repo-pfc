package model.business.control.CBR.similarity.local;

import java.io.Serializable;

/**
 * Class used to calculate the similarity between enum attributes.
 * This value is the their distance sim(x,y)=|ord(x) - ord(y)|
 */
public class EnumDistance implements LocalSimilarityFunction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6867487022372921018L;

	public double getSimilarity(Object value1, Object value2) {
		if ((value1 == null) || (value2 == null))
			return 0;
		
		Enum<?> enum1 = (Enum<?>)value1;
		Enum<?> enum2 = (Enum<?>)value2;
		
		double size = enum1.getDeclaringClass().getEnumConstants().length;
		double diff = Math.abs(enum1.ordinal() - enum2.ordinal());
		
		return 1 - (diff / size);
	}

}
