package model.business.knowledge;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents a Company
 */

@XmlAccessorType( XmlAccessType.FIELD )
public class Company implements Serializable {
	
	private static final long serialVersionUID = 807895690685697488L;
	
	private int id;
	private String cif; 
	private String name;
	private String information;
	@XmlElement( name = "Address" ) private Address address;
	
	public Company () {
	}
	
	public Company(String cif, String name, String information, Address address) {
		this.cif = cif;
		this.name = name;
		this.information = information;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCif() {
		return cif;
	}
	
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInformation() {
		return information;
	}
	
	public void setInformation(String information) {
		this.information = information;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Company:\n");
		result.append("      " + cif + "\n");
		result.append("      " + name + "\n");
		result.append("      " + information + "\n");
		result.append("      " + address + "\n");
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof Company) {
			Company other = (Company) obj;
			result = (cif.equals(other.getCif()) && name.equals(other.getName()) &&
					information.equals(other.getInformation()) && address.equals(other.getAddress()));
		}
		return result;
	}	
	
	public Object clone() {
		Company c;
		c = new Company(getCif(), getName(), getInformation(), (Address)getAddress().clone());
		c.setId(getId());
		return c;
	}
	
}

