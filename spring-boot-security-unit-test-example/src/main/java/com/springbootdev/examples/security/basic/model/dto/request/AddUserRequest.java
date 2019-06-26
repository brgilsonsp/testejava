package com.springbootdev.examples.security.basic.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

import com.springbootdev.examples.security.basic.model.dto.response.AddUserResponse;
import com.springbootdev.examples.security.basic.model.dto.response.FindUserResponse;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ApiModel(value = "User")
public class AddUserRequest implements Serializable {

    @NotNull(message = "Name is required")
    @ApiModelProperty(notes = "Name of the user")
    private String name;

    @NotNull(message = "Username is required")
    @ApiModelProperty(notes = "Username of the user")
    private String username;

    @NotNull(message = "Password is required")
    @ApiModelProperty(notes = "Password of the user")
    private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static AddUserRequest builder() {
		return new AddUserRequest();
	}

	public AddUserRequest name(String name) {
		this.name = name;
		return this;
	}

	public AddUserRequest username(String username) {
		this.username = username;
		return this;
	}

	public AddUserRequest password(String password) {
		this.password = password;
		return this;
	}

	public AddUserRequest build() {
		return this;
	}
    
    
}
