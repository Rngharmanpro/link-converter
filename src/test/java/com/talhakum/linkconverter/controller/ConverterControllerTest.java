package com.talhakum.linkconverter.controller;

import com.talhakum.linkconverter.kafka.KafkaRequestPublisher;
import com.talhakum.linkconverter.kafka.model.ConverterLogEvent;
import com.talhakum.linkconverter.service.ConverterService;
import com.talhakum.linkconverter.validator.ConverterValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Talha Kum
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverterControllerTest {

	@InjectMocks
	private ConverterController converterController;

	@Mock
	private ConverterService converterService;

	@Mock
	private ConverterValidator converterValidator;

	@Mock
	private KafkaRequestPublisher kafkaRequestPublisher;

	@Mock
	private MockHttpServletRequest httpServletRequest;

	@Mock
	private MockHttpServletResponse httpServletResponse;

	@Captor
	private ArgumentCaptor<ConverterLogEvent> converterLogEventCaptor;

	@Captor
	private ArgumentCaptor<String> urlCaptor;

	private ConverterLogEvent converterLogEvent;

	private static final String WEB_URL = "https://www.ecommerce.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
	private static final String DEEPLINK = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";

	@Before
	public void setUp() {
		converterLogEvent = new ConverterLogEvent();
	}

	@Test
	public void shouldReturnDeeplinkAndSuccessResponse() {
		when(converterService.convertWebUrlToDeeplink(any())).thenReturn(DEEPLINK);

		ResponseEntity<String> responseEntity = converterController.convertWebUrlToDeeplink(WEB_URL, null, httpServletRequest, httpServletResponse);
		assertThat(responseEntity.getBody(), equalTo(DEEPLINK));
		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void shouldPublishConverterLogEventInConvertWebUrlToDeeplink() {
		when(converterService.convertWebUrlToDeeplink(any())).thenReturn(DEEPLINK);

		converterLogEvent.setRequest(WEB_URL);
		converterLogEvent.setResponse(DEEPLINK);

		converterController.convertWebUrlToDeeplink(WEB_URL, null, httpServletRequest, httpServletResponse);

		verify(kafkaRequestPublisher).publish(converterLogEventCaptor.capture());
		assertThat(converterLogEventCaptor.getValue().getRequest(), equalTo(converterLogEvent.getRequest()));
		assertThat(converterLogEventCaptor.getValue().getResponse(), equalTo(converterLogEvent.getResponse()));
	}

	@Test
	public void shouldValidateInConvertWebUrlToDeeplink() {
		when(converterService.convertWebUrlToDeeplink(any())).thenReturn(DEEPLINK);

		converterController.convertWebUrlToDeeplink(WEB_URL, null, httpServletRequest, httpServletResponse);

		verify(converterValidator).validateConvertRequest(urlCaptor.capture());
		assertThat(urlCaptor.getValue(), equalTo(WEB_URL));
	}

	@Test
	public void shouldReturnWebUrlAndSuccessResponse() {
		when(converterService.convertDeeplinkToWebUrl(any())).thenReturn(WEB_URL);

		ResponseEntity<String> responseEntity = converterController.convertDeeplinkToWebUrl(DEEPLINK, null, httpServletRequest, httpServletResponse);
		assertThat(responseEntity.getBody(), equalTo(WEB_URL));
		assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void shouldPublishConverterLogEventInConvertDeeplinkToWebUrl() {
		when(converterService.convertDeeplinkToWebUrl(any())).thenReturn(WEB_URL);

		converterLogEvent.setRequest(DEEPLINK);
		converterLogEvent.setResponse(WEB_URL);

		converterController.convertDeeplinkToWebUrl(DEEPLINK, null, httpServletRequest, httpServletResponse);

		verify(kafkaRequestPublisher).publish(converterLogEventCaptor.capture());
		assertThat(converterLogEventCaptor.getValue().getRequest(), equalTo(converterLogEvent.getRequest()));
		assertThat(converterLogEventCaptor.getValue().getResponse(), equalTo(converterLogEvent.getResponse()));
	}

	@Test
	public void shouldValidateInConvertDeeplinkToWebUrl() {
		when(converterService.convertDeeplinkToWebUrl(any())).thenReturn(WEB_URL);

		converterController.convertDeeplinkToWebUrl(DEEPLINK, null, httpServletRequest, httpServletResponse);

		verify(converterValidator).validateConvertRequest(urlCaptor.capture());
		assertThat(urlCaptor.getValue(), equalTo(DEEPLINK));
	}

}
