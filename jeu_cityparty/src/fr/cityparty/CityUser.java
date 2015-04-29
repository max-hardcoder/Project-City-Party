package fr.cityparty;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Maxime
 *  *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CityUser {
	
	
	public CityUser(String id, String mail ) {
		this.key = id;
		this.mail = mail;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public String key;
	
//	@Persistent
//	public String id;
	
	@Persistent
	public String mail;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}




	

}
