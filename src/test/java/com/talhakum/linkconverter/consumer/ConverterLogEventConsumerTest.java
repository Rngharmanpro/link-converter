package com.talhakum.linkconverter.consumer;

import com.talhakum.linkconverter.kafka.model.ConverterLogEvent;
import com.talhakum.linkconverter.model.ConverterLog;
import com.talhakum.linkconverter.service.ConverterLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Talha Kum
 */
@RunWith(MockitoJUnitRunner.class)
public class ConverterLogEventConsumerTest {

	@InjectMocks
	private ConverterLogEventConsumer converterLogEventConsumer;

	@Mock
	private ConverterLogService converterLogService;

	@Captor
	private ArgumentCaptor<ConverterLog> converterLogCaptor;

	private ConverterLogEvent converterLogEvent;

	private Date createDate;

	private static final String WEB_URL = "https://www.ecommerce.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064";
	private static final String DEEPLINK = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";

	@Before
	public void setUp() {
		createDate = new Date();
		converterLogEvent = ConverterLogEvent.builder().accessUrl("/convert/deeplink-to-web").clientIp("127.0.0.1").createDate(createDate).request(WEB_URL).response(DEEPLINK)
				.build();
	}

	@Test
	public void shouldSaveConverterLogWhenConsume() {
		converterLogEventConsumer.process(converterLogEvent);

		verify(converterLogService).saveConverterLog(converterLogCaptor.capture());
		assertThat(converterLogCaptor.getValue().getAccessUrl(), equalTo(converterLogEvent.getAccessUrl()));
		assertThat(converterLogCaptor.getValue().getClientIp(), equalTo(converterLogEvent.getClientIp()));
		assertThat(converterLogCaptor.getValue().getCreateDate(), equalTo(converterLogEvent.getCreateDate()));
		assertThat(converterLogCaptor.getValue().getRequest(), equalTo(converterLogEvent.getRequest()));
		assertThat(converterLogCaptor.getValue().getResponse(), equalTo(converterLogEvent.getResponse()));
	}

}
