    ....
	
	this.glassPane = new InfiniteProgressPanel(ApplicationInternationalization.getString("glassLogin"));
    getMainFrame().setGlassPane(glassPane);
	
	....
	
	@Action
	public void loginAction() {
		....
		
		// Invoke a new thread in order to show the panel with the loading
		// spinner
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				glassPane.setColorB(241);
				glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						perform(txtUserName.getText(), new String(txtPass.getPassword()), ip, port);
					}
				}, "Performer");
				performer.start();
			}
		});		
	}
	
	private void perform(String user, String pass, String ip, String port) {		
		// Login
		ClientController.getInstance().initClient(ip, port, user, pass);
		glassPane.stop();
		getMainFrame().setEnabled(true);
		getMainFrame().requestFocus();
			
		....
	}