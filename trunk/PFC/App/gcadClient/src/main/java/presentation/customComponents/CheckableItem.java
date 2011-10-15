package presentation.customComponents;

import model.business.knowledge.User;

/**
 * This class represents a custom checkable item
 *
 */
// REFERENCE: http://www.java2s.com/Code/Java/Swing-Components/CheckListExample2.htm
public class CheckableItem {
	
	private User user;
	private boolean isSelected;

	public CheckableItem(User u) {
		this.user = u;
		isSelected = false;
	}

	public void setSelected(boolean b) {
		isSelected = b;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public String toString() {
		return user.toString();
	}

	public User getUser() {
		return user;
	}
}