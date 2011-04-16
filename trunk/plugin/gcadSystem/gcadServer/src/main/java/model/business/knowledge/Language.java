package model.business.knowledge;

import java.io.Serializable;

public class Language implements Serializable {

	private String name;
	private String code;
	
	public Language() { }	
	
	public Language(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public Object clone() {
		Language l;
		l = new Language();
		l.setCode(getCode());
		l.setName(getName());
		return l;
	}
	
}
