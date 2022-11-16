package com.xindog.message;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;

import java.util.HashMap;
import java.util.Map;

public class kafka extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "dory-01.srvs.cloudkafka.com:9094,dory-02.srvs.cloudkafka.com:9094,dory-03.srvs.cloudkafka.com:9094");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", "my_group");
        config.put("auto.offset.reset", "earliest");
        config.put("enable.auto.commit", "false");
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        String my_user = "";
        String my_password = "";
        config.put("sasl.jaas.config", PlainLoginModule.class.getName() + " required username=\"" + my_user + "\" password=\"" + my_password + "\";");

        KafkaProducer<String, String> producer = KafkaProducer.createShared(vertx, "the-producer", config);


        for (int i = 0; i < 5; i++) {

            // only topic and message value are specified, round robin on destination partitions
            KafkaProducerRecord<String, String> record =
                    KafkaProducerRecord.create("100pn0kj-test", "message_" + i);

            producer.write(record);

        }

        producer
                .partitionsFor("100pn0kj-test")
                .onSuccess(partitions ->
                        partitions.forEach(System.out::println)
                ).
                onFailure(throwable -> System.out.println("Error getting partitions" + throwable.getMessage()));

    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new kafka());
    }
}
