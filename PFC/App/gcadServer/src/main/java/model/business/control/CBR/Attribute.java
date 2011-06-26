package model.business.control.CBR;

import java.io.Serializable;

/**
 * This class represents an attribute of a case. Each attribute has name and type (class)
 */
public class Attribute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4991331355897873744L;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String toString() {
		return name;
	}
	
}
