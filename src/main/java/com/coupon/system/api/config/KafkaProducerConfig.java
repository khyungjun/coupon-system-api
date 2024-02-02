package com.coupon.system.api.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

	@Bean
	public ProducerFactory<String, Long> producerFactory() { // Producer 인스턴스를 생성하는데 필요한 설정 값들을 설정하기 위한 프로듀서 팩토리를 빈으로 등록하기 위한 메소드
		Map<String, Object> config = new HashMap<>();
		
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean
	public KafkaTemplate<String, Long> kafkaTemplate() { // 카프카 Topic에 데이터를 전송하기 위해 사용할 카프카 템플릿을 빈으로 등록하기 위한 메소드
		return new KafkaTemplate<>(producerFactory()); // 카프카 템플릿에 프로듀서 팩토리 전달
	}
}
