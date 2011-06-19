package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Company;
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
public class DAOCompany {

	private static final String COMPANY_CLASS = "Company";
	
	public static void insert(Company comp) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Company newComp = (Company) DBConnectionManager.insert(comp.clone());
			comp.setId(newComp.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
	}
}