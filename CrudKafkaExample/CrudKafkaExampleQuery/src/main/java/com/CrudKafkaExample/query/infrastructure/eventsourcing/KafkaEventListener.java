package com.CrudKafkaExample.query.infrastructure.eventsourcing;

import com.CrudKafkaExample.query.domain.model.Book;
import com.CrudKafkaExample.query.infrastructure.repository.BookRepository;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {

    //Logger
    private Logger logger = LoggerFactory.getLogger(KafkaEventListener.class);
    private BookRepository bookRepository;

    @Autowired
    public KafkaEventListener(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @KafkaListener(topics = "${message.topic.crud.create}", groupId = "id-topic")
    public void listenSave(ConsumerRecord<String, String> stringStringConsumerRecord){
        logger.info("Mensaje recibido 1er evento..!");
        Book book = new Gson().fromJson(stringStringConsumerRecord.value(), Book.class);
        bookRepository.save(book);
    }

    @KafkaListener(topics = "${message.topic.crud.update}", groupId = "id-topic2")
    public void listenUpdate(ConsumerRecord<String, String> stringStringConsumerRecord){
        logger.info("Mensaje recibido 3er evento..!");
        Book book = new Gson().fromJson(stringStringConsumerRecord.value(), Book.class);
        bookRepository.save(book);
    }

    @KafkaListener(topics = "${message.topic.crud.delete}", groupId = "id-topic3")
    public void listenDelete(ConsumerRecord<String, String> stringStringConsumerRecord){
        logger.info("Mensaje recibido 3er evento..!");
        Book book = new Gson().fromJson(stringStringConsumerRecord.value(), Book.class);
        bookRepository.delete(book);
    }



}