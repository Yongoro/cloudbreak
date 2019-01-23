package com.sequenceiq.it;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.structuredevent.event.StructuredFlowEvent;
import com.sequenceiq.cloudbreak.util.JsonUtil;

@Service
public class StructuredEventWaiter extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StructuredEventWaiter.class);

    private String awaitedStatus;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "StructuredEvents", containerFactory = "kafkaListenerContainerFactory")
    public void receiveStructureEvent(final String payload) {
        StructuredFlowEvent flowEvent = parseStructuredFlowEvent(payload);
        if (flowEvent.getFlow() != null) {
            LOGGER.info("CLUSTER STATUS: [{}]", flowEvent.getFlow().getFlowState());
            if (flowEvent.getFlow().getFlowState().equals(awaitedStatus)) {
                latch.countDown();
            }
        }
    }

    public void setAwaitedStatus(String awaitedStatus) {
        this.awaitedStatus = awaitedStatus;
    }

    public void reset() {
        latch = new CountDownLatch(1);
    }

    public StructuredFlowEvent parseStructuredFlowEvent(String payload) {
        try {
            return JsonUtil.readValue(payload, StructuredFlowEvent.class);
        } catch (IOException e) {
            LOGGER.debug("Parse not successful for payload: {}", payload);
        }
        return null;
    }
}