package model.business.knowledge;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType( XmlAccessType.FIELD )
public class Company {

	private int id;
	private String cif; 
	private String name;
	private String information;
	@XmlElement private Address address;
	
	public Company(int id, String cif, String name, String information, Address address) {
		this.id = id;
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
	
	
	
}

