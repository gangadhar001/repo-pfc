Constructor c = Class.forName("presentation.panelsManageKnowledge.JPManage"+subgroup).getConstructor(
				new Class [] {JFMain.class, JDialog.class, Object.class, String.class});
component = (Component) c.newInstance(new Object [] {parentFrame, dialog, data, operationToDo});