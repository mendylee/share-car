package com.jinzhun.auth2.constants;

public enum AuthoritiesEnum {

	/**
	 * 管理员
	 */
	ADMIN("ROLE_ADMIN"),
	/**
	 * 普通用户
	 */
	USER("ROLE_USER"),
	/**
	 * 匿名用户
	 */
	ANONYMOUS("ROLE_ANONYMOUS");

	private String role;

	AuthoritiesEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
