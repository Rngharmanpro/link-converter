package com.talhakum.linkconverter.validator;

import com.talhakum.linkconverter.fault.ConverterError;
import com.talhakum.linkconverter.fault.ConverterRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Talha Kum
 */
@Component
public class ConverterValidator {

	public void validateConvertRequest(String url) {
		notBlank(url, ConverterError.EC_INVALID_PARAMETER, "Url can not be empty!");
	}

	protected void notBlank(String string, ConverterError error, String message) {
		if (StringUtils.isBlank(string)) {
			throw new ConverterRuntimeException(error, message);
		}
	}

}
