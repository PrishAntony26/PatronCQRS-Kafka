package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.eventsourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaBookEventSourcing {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaBookEventSourcing(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Value(value = "${message.topic.crud.create}")
    private String topicNameCreate;

    @Value(value = "${message.topic.crud.update}")
    private String topicNameUpdate;

    @Value(value = "${message.topic.crud.delete}")
    private String topicNameDelete;

    public void createBookEvent(Book book) throws JsonProcessingException{
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(book);
        log.info(" Enviando mensaje al topico {}", topicNameCreate);
        kafkaTemplate.send(topicNameCreate, json);
    }

    public void updateBookEvent(Book book) throws JsonProcessingException{
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(book);
        log.info(" Enviando mensaje al topico {}", topicNameUpdate);
        kafkaTemplate.send(topicNameUpdate, json);
    }

    public void deleteBookEvent(Book book) throws JsonProcessingException{
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(book);
        log.info(" Enviando mensaje al topico {}", topicNameDelete);
        kafkaTemplate.send(topicNameDelete, json);
    }

    //Update - Delete

}
