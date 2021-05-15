package com.talhakum.linkconverter.fault;

/**
 * @author Talha Kum
 */
public class ConverterRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5198404766381125422L;

	private ConverterError converterError;

	public ConverterRuntimeException(ConverterError error, String message) {
		super(message);
		this.converterError = error;
	}

}
