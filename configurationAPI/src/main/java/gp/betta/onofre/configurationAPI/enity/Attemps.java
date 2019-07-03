package gp.betta.onofre.configurationAPI.enity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="attemp")
public class Attemps implements Serializable{
	
	private static final long serialVersionUID = 2166812263237712682L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@NotNull(message="it's necessary")
	@Positive(message="accept only positive number")
	@Column(nullable=false, unique=true)
	@JsonProperty(required=true, value="limitTry")
	private int limitTry;

	public Attemps() {
	}
	public Attemps(int limitTry) {
		this.limitTry = limitTry;
	}
	public Attemps(Integer id, int limit) {
		this(limit);
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getId() {
		return id;
	}
	
	public void alterValueLimit(int newLimit) {
		this.limitTry = newLimit;
	}
	/**
	 * Return the limit of the attemps, if not found the value, return the default value
	 * @return the value attemps value
	 */
	public Integer getLimitTry() {
		return this.limitTry;
	}
	
	public Boolean isAttempsNotValid() {
		return this.limitTry == 0;
	}
	
	@Override
	public String toString() {
		return "Attemps [id=" + id + ", limit=" + getLimitTry() + "]";
	}
	
}
