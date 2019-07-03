package gp.betta.onofre.configurationAPI.enity.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("prop.attemps.default")
public class AttempsProperties implements Serializable{

	private static final long serialVersionUID = 4854533562121357757L;

	private int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AttempsProperties [limit=" + limit + "]";
	}
	
}
