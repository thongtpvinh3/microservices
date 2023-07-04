package com.service.demomessagequeue.config;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import static com.service.demomessagequeue.config.Utility.*;

public class Initialize {

    public static void main(String[] args) {
        Properties producerConfig = baseKafkaProducerConfig();

        createTopic("topic-1", 4);
        createTopic("topic-2", 4);
        createTopic("topic-3", 2);

        KafkaProducer<String, String> prod1 = createProducer(producerConfig, new HashMap<>());
        KafkaProducer<String, String> prod2 = createProducer(producerConfig, new HashMap<>());

        sendMessage(prod1, "topic-1", "Topic-1: Message", 12, 2);
        sendMessage(prod2, "topic-2", "Topic-2: Message", 1000, -1);

        flushAndCloseProducer(Arrays.asList(prod1, prod2));
    }
}
