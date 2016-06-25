package cs.scrs.config.persistence;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Basic properties for Hibernate
 * @author ivan18
 */
@Configuration
@ConfigurationProperties(
		value = "hibernate",
		locations = "classpath:configurations/hibernate.properties",
		exceptionIfInvalid = true,
		ignoreInvalidFields = false, 
		ignoreUnknownFields = false
		)
public class Hibernate {

	private Hbm2ddl hbm2ddl;
	private String dialect;
	private String show_sql;
	
	public static class Hbm2ddl{
		private String auto;

		public String getAuto() {
			return auto;
		}

		public void setAuto(String auto) {
			this.auto = auto;
		}
		
	}
	public Hbm2ddl getHbm2ddl() {
		return hbm2ddl;
	}
	public void setHbm2ddl(Hbm2ddl hbm2ddl) {
		this.hbm2ddl = hbm2ddl;
	}
	
	
	public String getDialect() {
		return dialect;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public String getShow_sql() {
		return show_sql;
	}
	public void setShow_sql(String show_sql) {
		this.show_sql = show_sql;
	}
	
	@Override
	public String toString() {
		return "HibernateConfigProperties [hbm2ddlAuto=" + hbm2ddl.getAuto() + ", dialect=" + dialect + ", show_sql="
				+ show_sql + "]";
	}

}
