package com.service.demomessagequeue.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements Partitioner {

    private int index = 0;

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        Integer numPartitions = cluster.partitionCountForTopic(s);
        int partition = index % numPartitions;
        index++;
        return partition;
    }

    @Override
    public void close() {

    }
}
