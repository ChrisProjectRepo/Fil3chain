package cs.scrs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
/**
 * Basic properties for JPA
 * @author ivan18
 */
@ConfigurationProperties(
		prefix = "jpa",
		locations = "classpath:configurations/jpa.properties",
		exceptionIfInvalid = true
		)
public class JpaConfig {
	
	@NestedConfigurationProperty
	private Database database;

	
	/**
	 * @return the database
	 */
	public Database getDatabase() {
	
		return database;
	}

	
	/**
	 * @param database the database to set
	 */
	public void setDatabase(Database database) {
	
		this.database = database;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "JpaConfig [database=" + database + "]";
	}
	
	
}
