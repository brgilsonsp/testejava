package gp.betta.onofre.configurationAPI.enity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "token", name = "uniq_token") })
public class TokenPayment implements Serializable {

	private static final long serialVersionUID = 4081886125698347695L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank(message = "it's necessary")
	@Column(nullable = false, unique = true)
	@JsonProperty(required = true, value = "token")
	private String token;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public TokenPayment() {
	}

	public TokenPayment(String token) {
		this.token = token;
	}

	public void changeTokenValue(String newValueToken) {
		this.token = newValueToken;
	}

	public Boolean isTokenEmpty() {
		return this.token == null || this.token.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		TokenPayment other = (TokenPayment) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenPayment [id=" + id + ", token=" + token + "]";
	}

}