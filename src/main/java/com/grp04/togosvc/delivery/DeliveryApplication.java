package com.grp04.togosvc.delivery;

import com.grp04.togosvc.delivery.kafka.KafkaProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
public class DeliveryApplication {

	public static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DeliveryApplication.class, args);
	}

}
