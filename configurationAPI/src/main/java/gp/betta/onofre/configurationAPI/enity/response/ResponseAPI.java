package gp.betta.onofre.configurationAPI.enity.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ResponseAPI<T> implements Serializable {

	private static final long serialVersionUID = 5222414004624309096L;

	private String message;
	
	private T data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ResponseAPI [message=" + message + ", data=" + data + "]";
	}
	
}
