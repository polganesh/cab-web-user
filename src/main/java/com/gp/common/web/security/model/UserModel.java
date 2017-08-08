package com.gp.common.web.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {

	private Long userid;

	private String username;

	private String password;

	private Long roleid;

	private int isdeleted;

	public UserModel() {
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

}
