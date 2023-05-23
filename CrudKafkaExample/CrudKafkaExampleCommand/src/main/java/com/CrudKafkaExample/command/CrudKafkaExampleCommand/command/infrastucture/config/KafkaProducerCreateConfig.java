package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.config;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerCreateConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;



    public ProducerFactory<String, String> providerFactory(){
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    /***
     @Qualifierindica que un bean específico debe conectarse automáticamente cuando hay varios candidatos (Create - update - delete)
    * */
    @Bean("CreateConfig")
    public KafkaTemplate<String, String> kafkaTemplateCreate(){
        return new KafkaTemplate<>(providerFactory());
    }

    @Bean("CreateConfig2")
    public KafkaTemplate<String, String> kafkaTemplateCreate2(){
        return new KafkaTemplate<>(providerFactory());
    }


}
