package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Address;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.User;
import persistence.utils.HibernateQuery;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

/**
 * This class allows to query users from the database
 */
public class DAOAddress {

	private static final String ADDRESS_CLASS = "Address";

	public static void insert(Address add) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Address newAdd = (Address) DBConnectionManager.insert(add.clone());
			add.setId(newAdd.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
	}
}