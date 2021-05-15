package com.talhakum.linkconverter.validator;

import com.talhakum.linkconverter.fault.ConverterRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Talha Kum
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverterValidatorTest {

	@InjectMocks
	private ConverterValidator converterValidator;

	@Test(expected = ConverterRuntimeException.class)
	public void shouldThrowExceptionWhenUrlIsEmpty() {
		converterValidator.validateConvertRequest(StringUtils.EMPTY);
	}

}
