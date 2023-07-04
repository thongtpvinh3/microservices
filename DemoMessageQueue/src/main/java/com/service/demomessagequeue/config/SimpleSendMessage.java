package com.service.demomessagequeue.config;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import static com.service.demomessagequeue.config.Utility.*;

public class SimpleSendMessage {
    public static void main(String[] args) {
        int count = 1000;
        String message = "Send to Partition 1 message i";
        Properties producerConfig = baseKafkaProducerConfig();
        KafkaProducer<String, String> producer = createProducer(producerConfig, new HashMap<>());
        sendMessage(producer, "topic-3", message, count, 1);
        flushAndCloseProducer(Arrays.asList(producer));
    }
}
