// Create dataset for the chart of number of knowledge made on each project for that user
public AbstractDataset createDatasetKnowledgeDeveloper(User u, boolean percentage) throws RemoteException, NonPermissionRoleException, NotLoggedException, SQLException, Exception {
	AbstractDataset dataset = null;
	if (percentage)
		dataset = new DefaultPieDataset();
	else 
		dataset= new DefaultCategoryDataset();
	
	int totalCount = 0;
	int knowledgeUserCount;
	Hashtable<String, Integer> parcial_counts = new Hashtable<String, Integer>();
	
	// Get all projects
	List<Project> projects = ClientController.getInstance().getProjects();
	List<User> usersInProject; 
	for (Project p: projects) {
		// Get all the users that work in that project
		usersInProject = ClientController.getInstance().getUsersProject(p);
		if (usersInProject.contains(u)){
			// Get knowledge from user
			TopicWrapper tw = ClientController.getInstance().getTopicsWrapper(p);
			List<Knowledge> knowledgeUser = ClientController.getInstance().getKnowledgeUser(tw, u);  
			knowledgeUserCount = knowledgeUser.size();
			parcial_counts.put(p.getName(), knowledgeUserCount);
			// Use totalCount in order to calculate the percentage in the case of PieDataSet
			totalCount += knowledgeUserCount;
			// Bar chart case
			if (!percentage)
				((DefaultCategoryDataset)dataset).addValue(knowledgeUserCount, p.getName(), p.getName());			
		}
	}
	// Pie Chart case
	if (percentage) {
		for (String projectName: parcial_counts.keySet()) {
			double value = ((parcial_counts.get(projectName) * 100.0) / totalCount);
			((DefaultPieDataset)dataset).setValue(projectName, value);
		}
	}
	return dataset;
}