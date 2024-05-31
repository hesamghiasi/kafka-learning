package test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        var producer = new KafkaProducer<String, String>(kafkaProps);
        Future<RecordMetadata> test = producer.send(new ProducerRecord<>("test", "first-test"), (metadata, exception) -> {
            System.out.println(metadata.partition());
            System.out.println(exception);
        });
        System.out.println(test.get());
        
        var producer = new KafkaProducer<String, String>(kafkaProps);
        String s = new String("d");
        String v = s.intern();

       // producer.send(new ProducerRecord<>("test3", "first-test")).get().;
        Future<RecordMetadata> test = producer.send(new ProducerRecord<>("test3", "first-test"), (metadata, exception) -> {
            System.out.println(metadata.partition());
            System.out.println(exception);
        });

        System.out.println(test.get());
    }
}
