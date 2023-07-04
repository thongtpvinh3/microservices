//package com.service.demomessagequeue.config;
//
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewPartitions;
//import org.apache.kafka.clients.admin.NewPartitionsIncreaseMode;
//import org.apache.kafka.clients.admin.NewPartitionsRescaleMode;
//import org.apache.kafka.clients.admin.TopicDescription;
//import org.apache.kafka.common.KafkaFuture;
//import org.apache.kafka.common.TopicPartitionInfo;
//
//import java.util.Collections;
//import java.util.Properties;
//import java.util.concurrent.ExecutionException;
//
//public class AddPartitionExample {
//
//    public static void main(String[] args) {
//        // Set the admin client configuration properties
//        Properties props = new Properties();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//
//        // Create an instance of the admin client
//        try (AdminClient adminClient = AdminClient.create(props)) {
//
//            // Define the topic name and the desired number of partitions
//            String topicName = "my-topic";
//            int newPartitionCount = 5; // New total partition count
//
//            // Get the current topic description
//            KafkaFuture<TopicDescription> topicDescriptionFuture = adminClient.describeTopics(Collections.singleton(topicName)).values().get(topicName);
//            TopicDescription topicDescription = topicDescriptionFuture.get();
//
//            // Check if the topic already has the desired number of partitions
//            int currentPartitionCount = topicDescription.partitions().size();
//            if (currentPartitionCount >= newPartitionCount) {
//                System.out.println("The topic already has the desired number of partitions.");
//                return;
//            }
//
//            // Create new partitions request
//            NewPartitions newPartitions = NewPartitions.increaseTo(newPartitionCount);
//            newPartitions.validateOnly(); // This performs a dry run without actually adding partitions
//
//            // Increase the partition count of the topic
//            KafkaFuture<Void> increasePartitionsFuture = adminClient.createPartitions(Collections.singletonMap(topicName, newPartitions)).values().get(topicName);
//            increasePartitionsFuture.get();
//
//            // Verify the new partition count
//            topicDescriptionFuture = adminClient.describeTopics(Collections.singleton(topicName)).values().get(topicName);
//            topicDescription = topicDescriptionFuture.get();
//            currentPartitionCount = topicDescription.partitions().size();
//            System.out.println("New partition count: " + currentPartitionCount);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
