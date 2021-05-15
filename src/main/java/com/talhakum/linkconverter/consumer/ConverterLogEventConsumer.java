package com.talhakum.linkconverter.consumer;

import com.talhakum.linkconverter.kafka.KafkaConstants;
import com.talhakum.linkconverter.model.ConverterLog;
import com.talhakum.linkconverter.kafka.model.ConverterLogEvent;
import com.talhakum.linkconverter.service.ConverterLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Talha Kum
 */
@Service
public class ConverterLogEventConsumer {

	@Autowired
	private ConverterLogService converterLogService;

	@KafkaListener(//
			topics = KafkaConstants.TOPIC_NAME_CONVERTER_LOG_EVENT, //
			groupId = KafkaConstants.CONSUMER_GROUP_CONVERTER_LOG_EVENT, //
			containerFactory = "converterLogEventKafkaListenerContainerFactory")
	public void process(ConverterLogEvent converterLogEvent) {
		ConverterLog converterLog = buildConverterLog(converterLogEvent);
		converterLogService.saveConverterLog(converterLog);
	}

	private ConverterLog buildConverterLog(ConverterLogEvent converterLogEvent) {
		return ConverterLog.builder().accessUrl(converterLogEvent.getAccessUrl()).clientIp(converterLogEvent.getClientIp()).createDate(converterLogEvent.getCreateDate())
				.request(converterLogEvent.getRequest()).response(converterLogEvent.getResponse()).build();
	}

}
