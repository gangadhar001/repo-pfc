import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;




import model.business.knowledge.UserRole;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;


public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	    XMLConfiguration config;
	    
		List<String> rolesName;
		List<String> profiles;
		List<String> profilesName;
		List<String> actionsName;
		
		List<HierarchicalConfiguration> profilesConfiguration;
		List<HierarchicalConfiguration> actionsConfigurations;
		
		int index;
		String profile; 
		
		try
		{
			// Hacer una interfaz para tomar esta ruta
		    config = new XMLConfiguration("./profiles/profiles-role.xml");
		    // Se toman los roles definidos
		    rolesName = config.getList("Role.name");		    		
		    // Se comprueba en la lista de roles definidos en el sistema si existe ese rol
		    System.out.println(rolesName.contains(UserRole.Employee.toString()));
		    // Si existe, se toman sus perfiles
		    index = rolesName.indexOf(UserRole.ChiefProject.toString());
		    profilesConfiguration = config.configurationsAt("Role("+index+").Profile");
		    profiles = new ArrayList<String>();		    
		    for(Iterator it = profilesConfiguration.iterator(); it.hasNext();)
		    {
		        HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
		        // sub contains now all data about a single field
		        String profileName = sub.getString("name");
		        profiles.add(profileName);
		    }	
		    System.out.println("Perfiles: " + profiles);
			    
				
		// Una vez obtenidos los perfiles para ese rol, se consultan los item de los menus que son accesibles
		config = new XMLConfiguration("./profiles/profiles.xml");
		// Se toman los perfiles, para comprobar que realmente existe ese perfil y por si el orden no coincide exactamente
		profilesName = config.getList("Profile.name");
		actionsConfigurations = new ArrayList<HierarchicalConfiguration>();

		for (int i=0; i<profiles.size(); i++) {
			profile = profiles.get(i);
			// Si existe el perfil obtenido anteriormente, se toman sus acciones
			if (profilesName.contains(profile)) {
				index = profilesName.indexOf(profile);
				actionsConfigurations.addAll(config.configurationsAt("Profile("+index+").Actions"));
			}
		}

		// Se toman los nombres de las acciones
		actionsName = new ArrayList<String>();	
		for(Iterator it = actionsConfigurations.iterator(); it.hasNext();)
		    {
		        HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
		        // sub contains now all data about a single field
		        actionsName.addAll(sub.getList("name"));
		    }	
		System.out.println("Acciones para esos perfiles: " + actionsName);

		} catch(ConfigurationException ex)
		{
		    // something went wrong, e.g. the file was not found
		}
			 	
		

	}

}
