package com.qkwl.common.dto.user;

import java.io.Serializable;

/**
 * 登录后响应的用户信息
 * @author ZKF
 */
public class LoginTockenId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// token令牌
	private String token;
	// 签名key
	private Integer id;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
