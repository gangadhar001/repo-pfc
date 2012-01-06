package persistence;

import internationalization.AppInternationalization;

import java.sql.SQLException;
import java.util.List;

import model.business.knowledge.Address;
import persistence.utils.HibernateQuery;

import communication.DBConnectionManager;

import exceptions.NonExistentAddressException;

/**
 * This class allows to query and modify addresses from the database
 */
public class DAOAddress {

	private static final String ADDRESS_CLASS = "Address";
	private static final String COL_ID = "id";

	public static Address queryAddress(int id) throws NonExistentAddressException, SQLException {
		HibernateQuery query;
		List<?> data;
		Address add = null;
		
		query = new HibernateQuery("From " + ADDRESS_CLASS + " Where " + COL_ID + " = ?", id);
		data = DBConnectionManager.query(query);

		if(data.size() == 0)
			throw new NonExistentAddressException(AppInternationalization.getString("NonExistentAddressException"));
		
		add = (Address) ((Address)(data.get(0))).clone();
		
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
	
	public static void update(Address add) throws SQLException, NonExistentAddressException {
		HibernateQuery query;
		List<?> data;
		Address oldAdd = null;
		
		try {
			query = new HibernateQuery("From " + ADDRESS_CLASS + " Where id = ?", add.getId());
			data = DBConnectionManager.query(query);
	
			if(data.size() > 0) {
				oldAdd = (Address)data.get(0);									
			}
			else
				throw new NonExistentAddressException(AppInternationalization.getString("NonExistentAddressException"));
			
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
	
	public static void delete(Address address) throws SQLException, NonExistentAddressException {
		try {
			DBConnectionManager.initTransaction();
			DBConnectionManager.delete(queryAddress(address.getId()));
		} finally {
			DBConnectionManager.finishTransaction();
		}
	}
}
