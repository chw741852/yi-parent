package com.hong.core.exception;
public class UnauthorizedException extends CommonException {

	private static final long serialVersionUID = 2234821080173990684L;

	public UnauthorizedException(String code, String message) {
		super(code, message);
	}

	public UnauthorizedException() {
		this("401", "未经授权的请求");
	}

}