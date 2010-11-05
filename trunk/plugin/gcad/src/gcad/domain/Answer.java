package gcad.domain;

public class Answer {
	
	private String title;
	private String argument;
	
	public Answer() {
	}
	
	public Answer(String title, String argument) {
		super();
		this.title = title;
		this.argument = argument;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	
	

}
