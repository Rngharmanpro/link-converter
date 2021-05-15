package com.talhakum.linkconverter.kafka.config;

import com.talhakum.linkconverter.kafka.KafkaConstants;
import com.talhakum.linkconverter.kafka.model.ConverterLogEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Talha Kum
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Bean
	public ConsumerFactory<String, ConverterLogEvent> converterLogEventConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.CONSUMER_GROUP_CONVERTER_LOG_EVENT);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ConverterLogEvent.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ConverterLogEvent> converterLogEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ConverterLogEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(converterLogEventConsumerFactory());
		return factory;
	}

}
