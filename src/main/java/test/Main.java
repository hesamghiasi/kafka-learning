package test;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.nanoTime();
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("max.block.ms", 700);
//        kafkaProps.put("linger.ms", 700);
//        kafkaProps.put("buffer.memory", 16384);
//        kafkaProps.put("batch.size", 16384);
        kafkaProps.put("max.request.size", 94);
        var producer = new KafkaProducer<String, String>(kafkaProps);
        Future<RecordMetadata> test_1 = producer.send(new ProducerRecord<>("test", "first-test"));
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        /*for (int i=0; i < 8000; i++) {
            executorService.submit(() -> producer.send(new ProducerRecord<>("test", "first-test" + Thread.currentThread().getId())));
        }*/
        executorService.shutdown();
        long end = System.nanoTime();
        System.out.println("time: " + (end - start));
        test_1.get();

        /*var producer = new KafkaProducer<String, String>(kafkaProps);
        String s = new String("d");
        String v = s.intern();

       // producer.send(new ProducerRecord<>("test3", "first-test")).get().;
        Future<RecordMetadata> test = producer.send(new ProducerRecord<>("test3", "first-test"), (metadata, exception) -> {
            System.out.println(metadata.partition());
            System.out.println(exception);
        });*/

//        System.out.println(test.get());
    }
}

class MyCallback implements Callback{

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        System.out.println("partition chosen: " + metadata.partition());
        System.out.println(exception);
    }
}
