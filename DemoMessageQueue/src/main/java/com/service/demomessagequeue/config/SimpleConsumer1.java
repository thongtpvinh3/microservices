package com.service.demomessagequeue.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

import static com.service.demomessagequeue.config.Utility.*;

public class SimpleConsumer1 {
    public static void main(String[] args) {
        Properties consumerProp = baseKafkaConsumerConfig();
        Map<String, String> addProp = new HashMap<>();
        addProp.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group-1");
        List<String> topicNames = new ArrayList<>();
        topicNames.add("topic-3");
        KafkaConsumer<String, String> kafkaConsumer0 = createConsumer(consumerProp, addProp, topicNames);

        receiverMessage(kafkaConsumer0);
    }
}
