package com.service.demomessagequeue.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Utility {
    public static Properties baseKafkaProducerConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        //If the request fails, the producer can automatically retry,
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //Reduce the no of requests less than 0xx
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // Setting Round Robin algorithm
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }


    public static Properties baseKafkaConsumerConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // đọc các message của topic từ thời điểm hiển tại (default)
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    public static void createTopic(String topicName, int partitions) {
        Properties propsAdmin = new Properties();
        propsAdmin.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        try (AdminClient adminClient = AdminClient.create(propsAdmin)) {

            // Create a new topic with the specified name and partitions
            NewTopic newTopic = new NewTopic(topicName, partitions, (short) 1);
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static KafkaProducer<String, String> createProducer(Properties properties, Map<String, String> moreConfig) {
        properties.putAll(moreConfig);
        return new KafkaProducer<>(properties);
    }

    public static KafkaConsumer<String, String> createConsumer(Properties properties, Map<String, String> moreConfig, List<String> topicNames) {
        properties.putAll(moreConfig);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(topicNames);
        return kafkaConsumer;
    }

    public static void sendMessage(KafkaProducer<String, String> producer, String topicName, String message, int count, int specPartition) {
        if (specPartition != -1) {
            for (int i = 0; i < count; i++) {
                producer.send(new ProducerRecord<>(topicName, specPartition, "key " + i, message + i));
            }
        } else {
            for (int i = 0; i < count; i++) {
                producer.send(new ProducerRecord<>(topicName, "key " + i, message + i));
            }
        }
    }

    public static void receiverMessage(KafkaConsumer<String, String> consumer) {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
            consumer.commitSync();
//            break;
        }
    }

    public static void flushAndCloseProducer(List<KafkaProducer<String, String>> producers) {
        producers.forEach(producer -> {
            producer.flush();
            producer.close();
        });
    }
}
