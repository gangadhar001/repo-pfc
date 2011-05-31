package model.business.knowledge;

import java.io.Serializable;

/**
 *  This class represents a geographic coordinate
 */
public class Coordinates implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6381219307484976295L;
	private String latitude;
	private String longitude;
	
	public Coordinates(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
}
