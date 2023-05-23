package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.eventsourcing;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.config.KafkaProducerCreateConfig;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.config.KafkaProducerDeleteConfig;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.config.KafkaProducerUpdateConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Component
public class KafkaBookEventSourcingOneTopic {

    private static final Logger logger = LoggerFactory.getLogger(KafkaBookEventSourcingOneTopic.class);
    @Value("${message.topic.crud.general}")
    private String topic;


    @Qualifier("CreateConfig")
    private KafkaTemplate<String, String>  kafkaProducerCreateConfig;

    @Qualifier("UpdateConfig")
    private KafkaTemplate<String, String>  kafkaProducerUpdateConfig;

    @Qualifier("DeleteConfig")
    private KafkaTemplate<String, String> kafkaProducerDeleteConfig;

    @Qualifier("CreateConfig2")
    private KafkaTemplate<String, String> kafkaProducerCreateConfig2;


    @Autowired
    public KafkaBookEventSourcingOneTopic(@Qualifier("CreateConfig") KafkaTemplate<String, String>  kafkaProducerCreateConfig,
                                          @Qualifier("UpdateConfig") KafkaTemplate<String, String>  kafkaProducerUpdateConfig,
                                          @Qualifier("DeleteConfig") KafkaTemplate<String, String> kafkaProducerDeleteConfig,
                                          @Qualifier("CreateConfig2") KafkaTemplate<String, String> kafkaProducerCreateConfig2) {
        this.kafkaProducerCreateConfig = kafkaProducerCreateConfig;
        this.kafkaProducerUpdateConfig = kafkaProducerUpdateConfig;
        this.kafkaProducerDeleteConfig = kafkaProducerDeleteConfig;
        this.kafkaProducerCreateConfig2 = kafkaProducerCreateConfig2;
    }

    public void sendMessageToFirstPartition(Book book) throws JsonProcessingException {

        String json = convertObjectToString(book);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, null, json);
        CompletableFuture<SendResult<String, String>> future = kafkaProducerCreateConfig.send(record);
        future.whenComplete(new BiConsumer<SendResult<String, String>, Throwable>() {
            @Override
            public void accept(SendResult<String, String> stringStringSendResult, Throwable throwable) {
                if(throwable != null){
                    logger.error("Error: "+ throwable.getMessage());
                }else {
                    String book = stringStringSendResult.getProducerRecord().value();
                    logger.info("Mensaje enviado a la 1era particion create A1: "+ book);
                }
            }
        });
    }

    public void sendMessageToFirstPartition2(Book book) throws JsonProcessingException {

        String json = convertObjectToString(book);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, null, json);
        CompletableFuture<SendResult<String, String>> future = kafkaProducerCreateConfig2.send(record);
        future.whenComplete(new BiConsumer<SendResult<String, String>, Throwable>() {
            @Override
            public void accept(SendResult<String, String> stringStringSendResult, Throwable throwable) {
                if(throwable != null){
                    logger.error("Error: "+ throwable.getMessage());
                }else {
                    String book = stringStringSendResult.getProducerRecord().value();
                    logger.info("Mensaje enviado a la 1era particion create A2: "+ book);
                }
            }
        });
    }

    public void sendMessageToSecondPartition(Book book) throws JsonProcessingException{
        String json = convertObjectToString(book);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, 1, null, json);
        CompletableFuture<SendResult<String, String>> future = kafkaProducerUpdateConfig.send(record);
        future.whenComplete(new BiConsumer<SendResult<String, String>, Throwable>() {
            @Override
            public void accept(SendResult<String, String> stringStringSendResult, Throwable throwable) {
                if(throwable != null){
                    logger.error("Error: "+ throwable.getMessage());
                } else {
                    String book = stringStringSendResult.getProducerRecord().value();
                    logger.info("Mensaje enviado a la 2da particion - update: "+ book);
                }
            }
        });
    }

    public void sendMessageToThirdPartition(Book book) throws JsonProcessingException{

        String json = convertObjectToString(book);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, 2, null, json);
        CompletableFuture<SendResult<String, String>> future = kafkaProducerUpdateConfig.send(record);
        future.whenComplete(new BiConsumer<SendResult<String, String>, Throwable>() {
            @Override
            public void accept(SendResult<String, String> stringStringSendResult, Throwable throwable) {
                if(throwable != null){
                    logger.error("Error: "+ throwable.getMessage());
                } else {
                    String book = stringStringSendResult.getProducerRecord().value();
                    logger.info("Mensaje enviado a la 3ra particion - delete: "+ book);
                }
            }
        });
    }

    //Objeto a String -> Json
    private String convertObjectToString(Object object) throws JsonProcessingException{
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(object);
        return json;
    }

}
