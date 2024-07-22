package com.homel.kafka;

import com.homel.kafka.prop.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class KafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}


	@Bean
	@ConfigurationProperties(prefix = "fsrv.kafka")
	public KafkaProperties kafkaFSRVProperties() {
		return new KafkaProperties();
	}

}
