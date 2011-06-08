package model.business.control.CBR.similarity.local;

/**
 * Class used to calculate the similarity between attributes.
 * This value is 1 if both are equals, and 0 otherwise
 */
public class Equal implements LocalSimilarityFunction {

    public double getSimilarity(Object value1, Object value2) {
        if ((value1 == null) || (value2 == null))
            return 0;
        return value1.equals(value2) ? 1 : 0;
    }
    
}
