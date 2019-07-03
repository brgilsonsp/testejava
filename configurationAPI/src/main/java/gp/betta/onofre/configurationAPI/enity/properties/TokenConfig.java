package gp.betta.onofre.configurationAPI.enity.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("prop.configapi.token")
public class TokenConfig implements Serializable{

	private static final long serialVersionUID = -2625720972237780410L;
	
	private String key;
	private String value;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Verify if the otherToken is equals the token valid
	 * @param otherToken
	 * @return true if the token is valid, otherwise return false
	 */
	public Boolean isTokenValid(String otherToken) {
		if(this.value == null || this.value.isEmpty() || otherToken == null || otherToken.isEmpty())
			return false;
		
		return this.value.equals(otherToken);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenConfig other = (TokenConfig) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenConfig [key=" + key + ", value=" + value + "]";
	}

}
