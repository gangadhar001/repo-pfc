package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import persistence.commands.SQLCommand;
import persistence.commands.SQLCommandSentence;
import persistence.communications.DBConnectionManager;

public class PFTopic {
	
	private static final String TOPIC_TABLE = "topics";
	
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_PROJECT_ID = "projectId";

	public static ArrayList<Topic> queryTopicsProject(int projectId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SQLCommand command;
		ResultSet data;
		Topic topic;
		ArrayList<Topic> topics = new ArrayList<Topic>();
		ArrayList<Proposal> proposals;
		
		command = new SQLCommandSentence("SELECT * FROM " + TOPIC_TABLE
				+ " WHERE " + COL_PROJECT_ID + " = ?", projectId);
		data = DBConnectionManager.query(command);
		data.next();
		
		// If data are not obtained, it is because there are no topics for this project
		if(data.getRow() > 0) {
			do {
				topic = new Topic();
				topic.setId(data.getInt(COL_ID));
				topic.setTitle(data.getString(COL_NAME));
				// TODO: set el project del topic
				// Set proposals of this topic
				proposals = PFProposal.queryProposalsTopic(topic.getId());
				topic.setProposals(proposals);
				topics.add(topic);
			} while(data.next());
			data.close();
		}
		return topics;
	}

}
