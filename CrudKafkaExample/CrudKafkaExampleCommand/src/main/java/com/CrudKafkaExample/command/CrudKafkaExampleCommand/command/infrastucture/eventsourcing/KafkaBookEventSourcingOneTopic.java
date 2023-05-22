package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.eventsourcing;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.config.KafkaProducerCreateConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaBookEventSourcingOneTopic {

    @Value("${message.topic.crud.general}")
    private String topic;
    private KafkaProducerCreateConfig kafkaProducerCreate;

    @Autowired
    public KafkaBookEventSourcingOneTopic(KafkaProducerCreateConfig kafkaProducerCreate) {
        this.kafkaProducerCreate = kafkaProducerCreate;
    }

    public void sendMessageToPartition(Book book) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(book);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, 0, null, json);
        kafkaProducerCreate.kafkaTemplate(kafkaProducerCreate.providerFactory()).send(record);
    }

}
