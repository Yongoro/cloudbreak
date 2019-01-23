package com.sequenceiq.it;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.rule.KafkaEmbedded;

@EnableKafka
@Configuration
public class StructuredEventTestListener extends SpringBootServletInitializer {


    private static final Logger LOGGER = LoggerFactory.getLogger(StructuredEventTestListener.class);

    private static final String ZKHOST = "127.0.0.1";

    private static final String BROKERHOST = "127.0.0.1";

    private static final String BROKERPORT = "9092";

    private static final String TOPIC = "test";

    @Autowired
    private KafkaEmbedded kafkaEmbedded;

    public static void main(String[] args) {
        SpringApplication.run(StructuredEventTestListener.class, args);
    }

//    @KafkaListener(topics = "StructuredEvents", containerFactory = "kafkaListenerContainerFactory")
//    public void receiveStructureEvent(final String payload) {
//        LOGGER.trace("Received payload: {}", payload);
//    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
//        factory.setAutoStartup(false);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        String brokerHost = "localhost:3333";
        LOGGER.info("STARTING EMBEDDED KAFKA ON {}", brokerHost);
        Map<String, Object> props = new HashMap<>();
        //this might not be needed, now it's here just to be safe
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerHost);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "1111");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Bean
    public KafkaEmbedded createBroker() {
        KafkaEmbedded broker = new KafkaEmbedded(1, true);
        Map<String, String> brokerProperties = new HashMap<>();
        brokerProperties.put("listeners", "PLAINTEXT://localhost:3333");
        brokerProperties.put("port", "3333");
        brokerProperties.put("auto.create.topics.enable", "true");

        broker.brokerProperties(brokerProperties);
        return broker;
    }
}