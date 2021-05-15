package com.talhakum.linkconverter.kafka;

import com.talhakum.linkconverter.kafka.model.ConverterLogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Talha Kum
 */
@Component
public class KafkaRequestPublisher {

	@Autowired
	private KafkaTemplate<String, ConverterLogEvent> kafkaTemplate;

	public void publish(ConverterLogEvent converterLogEvent) {
		kafkaTemplate.send(KafkaConstants.TOPIC_NAME_CONVERTER_LOG_EVENT, converterLogEvent);
	}

}
