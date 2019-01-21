package com.sequenceiq.it.cloudbreak.newway.context;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StructuredEventTestListener {


    private static final Logger LOGGER = LoggerFactory.getLogger(StructuredEventTestListener.class);

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    public static void main(String[] args) {
        SpringApplication.run(StructuredEventTestListener.class, args);
    }

    @KafkaListener(topics = "cbStructuredRestCallEvent", groupId = "testListener")
    public void receiveDunningHead(final String payload) {
        LOGGER.debug("Receiving event with payload [{}]", payload);
        //I will do database stuff here which i could check in db for testing
    }


    @Bean
    public EmbeddedKafkaBroker createBroker() {
        EmbeddedKafkaBroker broker = new EmbeddedKafkaBroker(1, false, "cbStructuredRestCallEvent");
        Map<String,String> brokerProperties =  new HashMap<>();
        brokerProperties.put("listeners", "PLAINTEXT://0.0.0.0:3333");
        brokerProperties.put("port", "3333");
        broker.brokerProperties(brokerProperties);
        return broker;
    }
}