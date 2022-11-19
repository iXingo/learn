package com.xindog.message;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaVerticle extends AbstractVerticle {

    private final String topic;
    private final Properties props;

    public KafkaVerticle(String brokers, String username, String password) {
        this.topic = username + "-default";

        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", username + "-producer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("sasl.jaas.config", jaasCfg);
    }

    @Override
    public void start() throws Exception {
        try {
            KafkaProducer<String, String> producer = KafkaProducer.create(vertx, this.props);
            System.out.println(producer);
            for (int i = 0; i < 5; i++) {
                // only topic and message value are specified, round robin on destination partitions
                KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(this.topic, "message_" + i);
                producer.write(record);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        String brokers = "dory-01.srvs.cloudkafka.com:9094,dory-02.srvs.cloudkafka.com:9094,dory-03.srvs.cloudkafka.com:9094";
        KafkaVerticle kafka = new KafkaVerticle(brokers,"100pn0kj", "hXCiaaoHQ10GeC3Vq1BCs0HpowPrOgBW");
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(kafka);
    }
}
