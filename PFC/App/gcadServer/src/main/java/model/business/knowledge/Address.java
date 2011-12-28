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
	private String code;
	
	public Address () {
	}
	
	public Address(String street, String city, String country, String zip, String code) {
		this.street = street;
		this.city = city;
		this.country = country;
		this.zip = zip;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Address: ");
		result.append("      " + street + " ");
		result.append("      " + city + " " + zip + " ");
		result.append("      " + country + " ");
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
		else if (obj instanceof Address) {
			Address other = (Address) obj;
			result = (street.equals(other.getStreet()) && city.equals(other.getCity()) &&
					country.equals(other.getCountry()) && zip.equals(other.getZip()) &&
					code.equals(getCode()));
		}
		return result;
	}
	
	public Object clone () {
		Address a;
		a = new Address(getStreet(), getCity(), getCountry(), getZip(), getCode());
		a.setId(getId());
		return a;
	}
}
