package com.github.hackerwin7.jd.lib.executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2017/10/24
 * Time: 10:44 AM
 * Desc:
 */
public class KafkaExecutor {
    public static void main(String[] args) {
        KafkaExecutor executor = new KafkaExecutor();
        System.out.println("producing...");
        executor.produce(args[0], args[1], Integer.parseInt(args[2]));
        System.out.println("consuming...");
        executor.consume(args[0], args[1]);
    }

    public void produce(String brokers, String topic,int msgCount) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.kerberos.service.name", "kafka");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < msgCount; i++)
            producer.send(new ProducerRecord<String, String>(topic, "msg-" + i + System.currentTimeMillis()));
        producer.close();
    }

    public void consume(String brokers, String... topics) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.kerberos.service.name", "kafka");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topics));
        consumer.poll(0); // assign topic partitions
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topics[0], 0))); // default empty args
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            System.out.println("==> " + records.count() + " records;");
            if(records.count() == 0)
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("!!! interrupt exception, skip it.");
                }
            else
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("\t topic = %s, partition = %s, offset = %d, key = %s, val = %s%n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
        }
    }
}
