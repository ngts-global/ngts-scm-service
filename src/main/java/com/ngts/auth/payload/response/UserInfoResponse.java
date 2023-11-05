package com.ngts.auth.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserInfoResponse {

	private Integer id;
	private String username;
	private String email;
	private List<String> roles;
	private String roleName;
	private List<?> functionsList;
	private int timeoutValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<?> getFunctionsList() {
		return functionsList;
	}

	public void setFunctionsList(List<?> functionsList) {
		this.functionsList = functionsList;
	}

	public int getTimeoutValue() {
		return timeoutValue;
	}

	public void setTimeoutValue(int timeoutValue) {
		this.timeoutValue = timeoutValue;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
