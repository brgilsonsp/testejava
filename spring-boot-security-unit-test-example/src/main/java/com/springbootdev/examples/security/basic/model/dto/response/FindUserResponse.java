package com.springbootdev.examples.security.basic.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
public class FindUserResponse implements Serializable {
    @JsonProperty("user_id")
    private Integer userId;
    private String name;
    private String username;
    
    
	public static FindUserResponse builder() {
		return new FindUserResponse();
	}

	public FindUserResponse userId(Integer id) {
		this.userId = id;
		return this;
	}

	public FindUserResponse name(String name) {
		this.name = name;
		return this;
	}

	public FindUserResponse username(String username) {
		this.username = username;
		return this;
	}

	public FindUserResponse build() {
		return this;
	}


}
