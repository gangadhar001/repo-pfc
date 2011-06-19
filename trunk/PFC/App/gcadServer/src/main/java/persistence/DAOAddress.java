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
import exceptions.IncorrectAddressException;
import exceptions.IncorrectEmployeeException;
import exceptions.NonExistentRole;

/**
 * This class allows to query users from the database
 */
public class DAOAddress {

	private static final String ADDRESS_CLASS = "Address";
	private static final String COL_ID = "id";

	public static Address queryAddress(int id) throws IncorrectAddressException, SQLException, NonExistentRole {
		HibernateQuery query;
		List<?> data;
		Address add = null;
		
		query = new HibernateQuery("From " + ADDRESS_CLASS + " Where " + COL_ID + " = ?", id);
		data = DBConnectionManager.query(query);

		// TODO
		if(data.size() == 0) {
			throw new IncorrectAddressException("El nombre de usuario o contraseña introducidos no son válidos.");
		} else {
			add = (Address) ((Address)(data.get(0))).clone();			
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}
		
		return add;
	}
	
	public static void insert(Address add) throws SQLException {
		try {
			DBConnectionManager.initTransaction();
			Address newAdd = (Address) DBConnectionManager.insert(add.clone());
			add.setId(newAdd.getId());
		} finally {
			DBConnectionManager.finishTransaction();
		}		
	}
	
	public static void update(Address add) throws SQLException {
		// Get the proposal stores in database and update that reference 
		HibernateQuery query;
		List<?> data;
		Address oldAdd = null;
		
		try {
			query = new HibernateQuery("From " + ADDRESS_CLASS + " Where id = ?", add.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				oldAdd = (Address)data.get(0);									
			}
			
			DBConnectionManager.initTransaction();	
			
			oldAdd.setCity(add.getCity());
			oldAdd.setCountry(add.getCountry());
			oldAdd.setId(add.getId());
			oldAdd.setStreet(add.getStreet());
			oldAdd.setZip(add.getZip());

			DBConnectionManager.update(oldAdd);
		} finally {
			DBConnectionManager.finishTransaction();
		}
		
		// Clear cache
		for(Object object : data) {
			DBConnectionManager.clearCache(object);
		}		
	}
	
	public static void delete(Address address) throws SQLException, IncorrectAddressException, NonExistentRole {
		try {
			DBConnectionManager.initTransaction();
			// Get the proposal stores in database and delete that reference 
			DBConnectionManager.delete(queryAddress(address.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}
