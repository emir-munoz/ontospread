package resources;

import java.util.ResourceBundle;

public class ApplicationResources {

	private static ResourceBundle resources = null;
	
	public static String getString(String key) {
		
		if ( resources == null ) {
			resources = ResourceBundle
			.getBundle(ApplicationResources.class.getName().toString());
		}
		
		return resources.getString(key);
	}
	 
}

