package model.business.knowledge;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * This class represents an Address
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Address implements Serializable {

	private static final long serialVersionUID = -2427789923156077868L;
	
	private int id;
	private String street;
	private String city;
	private String country;
	private String zip;
	
	public Address () {
	}
	
	public Address(String street, String city, String country, String zip) {
		this.street = street;
		this.city = city;
		this.country = country;
		this.zip = zip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Address:\n");
		result.append("      " + street + "\n");
		result.append("      " + city + " " + zip + "\n");
		result.append("      " + country + "\n");
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (id != other.id)
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	public Object clone () {
		Address a;
		a = new Address(getStreet(), getCity(), getCountry(), getZip());
		a.setId(getId());
		return a;
	}
}
