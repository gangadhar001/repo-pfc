package model.business.control.CBR;

import java.io.Serializable;
import java.util.HashMap;

import model.business.control.CBR.similarity.local.LocalSimilarityFunction;

/***
 * Class used to store the configuration of the NN algorithm
 */
public class ConfigCBR implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7511795271856085518L;
	// Stores the local similarity function for each attribute and its weights
	private HashMap<Attribute, LocalSimilarityFunction> localFunctions = new HashMap<Attribute, LocalSimilarityFunction>();
	private HashMap<Attribute, Double> weights = new java.util.HashMap<Attribute, Double>();	

	public ConfigCBR()
	{
	}

	public void addLocalSimFunction(Attribute attribute, LocalSimilarityFunction similFunction)
	{
		localFunctions.put(attribute, similFunction);
	}
	
	public LocalSimilarityFunction getLocalSimilFunction(Attribute attribute)
	{
		return localFunctions.get(attribute);
	}

	public void setWeight(Attribute attribute, double weight)
	{
		weights.put(attribute, weight);
	}

	public double getWeight(Attribute attribute)
	{
		Double d = weights.get(attribute);
		double value = 1;
		if(d != null)
			value = d.doubleValue();
		return value; 
	}
}
