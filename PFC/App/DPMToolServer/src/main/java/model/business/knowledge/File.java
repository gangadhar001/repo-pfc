package model.business.knowledge;

import java.io.Serializable;

/**
 * This class represents a File (txt, pdf, image, etc)
 */
public class File implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3741536334685997137L;
	private int id;
	private String fileName;
	private byte[] content;
	
	public File () {
	}
	
	public File(String fileName, byte[] content) {
		this.fileName = fileName;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("FileName: ");
		result.append(fileName);
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
		else if (obj instanceof File) {
			File other = (File) obj;
			result = (fileName.equals(other.getFileName()) && content.equals(other.getContent()));
		}
		return result;
	}
	
	public Object clone () {
		File a;
		a = new File(getFileName(), getContent());
		a.setId(getId());
		return a;
	}
}
