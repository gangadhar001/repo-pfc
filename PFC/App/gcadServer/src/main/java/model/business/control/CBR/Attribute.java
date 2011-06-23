package model.business.control.CBR;

import java.io.Serializable;

/**
 * This class represents an attribute of a case. Each attribute has name and type (class)
 */
public class Attribute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4643433099138548613L;
	private String name;
	private Class<?> type;
	
	
	// Creates an attribute
	public Attribute(String name, Class<?> type)
	{
		this.name = name;
		this.type = type;
	}	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Class<?> getType() {
		return type;
	}


	public void setType(Class<?> type) {
		this.type = type;
	}
	
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Attribute))
			return false;
		return  (name.equals(((Attribute)o).getName()))
				&&(type.equals(((Attribute)o).getType()));
	}

//	/**
//	 * Returns the value of the attribute for a concrete object. Of course, the object must be instance of the class that this attribute belongs to.
//	 * @param obj Instance to obtain the attribute from
//	 * @throws AttributeAccessException
//	 */
//	public Object getValue(Object obj) throws AttributeAccessException
//	{
//		Object res = null;
//		try{
//			res = field.get(obj);
//			return res;
//		}catch(Exception e)
//		{}
//
//		try{
//			java.beans.PropertyDescriptor pd = new java.beans.PropertyDescriptor(field.getName(),field.getDeclaringClass());
//			res = pd.getReadMethod().invoke(obj, (Object[])null);
//			return res;
//		}catch(Exception e)
//		{}
//		throw new AttributeAccessException("Error getting value from object: "+obj+", attribute: "+field.getName());
//
//	}
//	
//	/**
//	 * Sets the value of the attribute in a concrete object.
//	 * @param obj Object that defines the attribute to set.
//	 * @param value Value to set.
//	 * @throws AttributeAccessException
//	 */
//	public void setValue(Object obj, Object value) throws AttributeAccessException
//	{
//		try{
//			field.set(obj, value);
//		}catch(Exception e)
//		{}
//
//		try{
//			java.beans.PropertyDescriptor pd = new java.beans.PropertyDescriptor(field.getName(),field.getDeclaringClass());
//			Object[] args = {value};
//			pd.getWriteMethod().invoke(obj, args);
//		}catch(Exception e)
//		{
//			throw new AttributeAccessException("Error setting value from object: "+obj+", attribute: "+field.getName());
//		}
//
//	}	
	
	
	
	
	
}
