package com.productms.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productms.exception.ProductException;

@Service
public class KafkaService {

	static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);
	
	@Autowired
	private ProductService productService;
	
	@KafkaListener(id="order_placed", topics = "order_place_event")
	public void orderPlacedListener(String jsonPayload) throws ProductException {
		ObjectMapper mapper = new ObjectMapper();
        Map<String, String> itemMap = null;
		try {
			itemMap = mapper.readValue(jsonPayload, new TypeReference<Map<String, String>>() {});
			
			Long prodId = Long.parseLong(itemMap.get("id"));
	        Integer quantity = Integer.parseInt(itemMap.get("quantity"));
			
			LOGGER.info("Received Order Placed Request for product ID {} with quantity {}", prodId, quantity);
			productService.deductQuantity(prodId, quantity);
			LOGGER.info("Deducted quantity {} for product ID {}", quantity, prodId);
		} catch (JsonProcessingException | NumberFormatException e) {
	        LOGGER.error("Failed to process Kafka message: {}", jsonPayload, e);
	        // Optionally send to a dead-letter topic or alert system
	    }
		
	}
}
