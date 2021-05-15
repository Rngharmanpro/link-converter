package com.talhakum.linkconverter.fault;

/**
 * @author Talha Kum
 */
public enum ConverterError {

	EC_INVALID_PARAMETER(2001, 400, "A parameter is missing or not in required type");

	private final int errorCode;
	private final int httpStatusCode;
	private final String errorMsg;

	ConverterError(int errorCode, int httpStatusCode, String errorMsg) {
		this.errorCode = errorCode;
		this.httpStatusCode = httpStatusCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
