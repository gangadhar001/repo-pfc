package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.DBConnectionManager;

import model.business.knowledge.Address;
import model.business.knowledge.Company;
import model.business.knowledge.Notification;
import model.business.knowledge.Project;
import model.business.knowledge.Proposal;
import model.business.knowledge.User;
import persistence.utils.HibernateQuery;
import exceptions.IncorrectAddressException;
import exceptions.IncorrectCompanyException;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

/**
 * This class allows to query users from the database
 */
public class DAOCompany {

	private static final String COMPANY_CLASS = "Company";
	private static final String COL_ID = "id";
	
	public static void insert(Company comp) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Company newComp = (Company) DBConnectionManager.insert(comp.clone());
			comp.setId(newComp.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
	}

	public static Company queryCompany(int id) throws SQLException, IncorrectCompanyException {
		HibernateQuery query;
		List<?> data;
		Company comp = null;
		
		query = new HibernateQuery("From " + COMPANY_CLASS + " Where " + COL_ID + " = ?", id);
		data = DBConnectionManager.query(query);

		// TODO
		if(data.size() == 0) {
			throw new IncorrectCompanyException("El nombre de usuario o contraseña introducidos no son válidos.");
		} else {
			comp = (Company) ((Company)(data.get(0))).clone();			
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return comp;
	}
	
	public static void update(Company comp) throws SQLException {
		// Get the proposal stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Company oldComp = null;
		
		try {
			query = new HibernateQuery("From " + COMPANY_CLASS + " Where " + COL_ID + " = ?", comp.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				oldComp = (Company)data.get(0);									
			}
			
			DBConnectionManager.initTransaction();	
			
			oldComp.setAddress(comp.getAddress());
			oldComp.setCif(comp.getCif());
			oldComp.setId(comp.getId());
			oldComp.setInformation(comp.getInformation());
			oldComp.setName(comp.getName());

			DBConnectionManager.update(oldComp);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}		
	}
	
	public static void delete(Company comp) throws SQLException, IncorrectAddressException, NonExistentRole, IncorrectCompanyException {
		try {
			DBConnectionManager.initTransaction();
			// Get the proposal stores in database and delete that reference 
			DBConnectionManager.delete(queryCompany(comp.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}