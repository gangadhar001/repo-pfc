package model.business.control.CBR.similarity.global;

import java.util.List;

import model.business.control.ProjectController;
import model.business.control.CBR.Attribute;
import model.business.control.CBR.retrieveAlgorithms.NNConfig;
import model.business.control.CBR.similarity.local.LocalSimilarityFunction;
import model.business.knowledge.Project;


/**
 * TODO
 */
public interface GlobalSimilarityFunction {

  

    
    
    /**
     * Hook method that must be implemented by subclasses returned the global similarity value.
     * @param values of the similarity of the sub-attributes
     * @param weigths of the sub-attributes
     * @param numberOfvalues (or sub-attributes) that were obtained (some subattributes may not compute for the similarity).
     * @return a value between [0..1]
     */
    public double computeSimilarity(double[] values, double[] weigths, int numberOfvalues);
}
