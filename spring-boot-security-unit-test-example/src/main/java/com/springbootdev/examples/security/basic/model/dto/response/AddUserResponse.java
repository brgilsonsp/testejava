package com.springbootdev.examples.security.basic.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class AddUserResponse implements Serializable {

    @JsonProperty("user_id")
    private Integer userId;

    private String username;

    @JsonProperty("created_on")
    private String createdOn;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public static AddUserResponse builder() {
		return new AddUserResponse();
	}

	public AddUserResponse userId(Integer userId) {
		this.userId = userId;
		return this;
	}

	public AddUserResponse username(String username) {
		this.username = username;
		return this;
	}

	public AddUserResponse createdOn(String createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public AddUserResponse build() {
		return this;
	}
    
    
}
