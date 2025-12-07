package com.productms.utility;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class KafkaUtitily {

	@Autowired
	private KafkaTemplate<String, String> template;
	
	@Bean
	NewTopic productDeleteEvent() {
		return TopicBuilder.name("product_delete_event").partitions(1).replicas(1).build();
	}
	
	public void productDeleted(String productId) {
		template.send("product_delete_event", "Product ID", productId);
	}
}
