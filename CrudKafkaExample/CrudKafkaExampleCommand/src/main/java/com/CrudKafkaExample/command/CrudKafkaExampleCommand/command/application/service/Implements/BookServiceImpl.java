package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.application.service.Implements;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.dto.CreateBookRequest;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.eventsourcing.KafkaBookEventSourcingOneTopic;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.application.service.BookService;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.eventsourcing.KafkaBookEventSourcing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    //private final KafkaBookEventSourcing kafkaBookEventSourcing;

    //Implementación de particiones en un topico
    private final KafkaBookEventSourcingOneTopic kafkaBookEventSourcingOneTopic;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           //KafkaBookEventSourcing kafkaBookEventSourcing,
                           KafkaBookEventSourcingOneTopic kafkaBookEventSourcingOneTopic){
        this.bookRepository = bookRepository;
        //this.kafkaBookEventSourcing = kafkaBookEventSourcing;
        this.kafkaBookEventSourcingOneTopic = kafkaBookEventSourcingOneTopic;
    }

    @Override
    public void createBook(CreateBookRequest createBookRequest) {
        Book book = CreateBookRequest.dtoToModel(createBookRequest);
        bookRepository.save(book);
        //Enviando el mensaje
        try {
            //kafkaBookEventSourcing.createBookEvent(book);
            kafkaBookEventSourcingOneTopic.sendMessageToFirstPartition(book);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBook(Long id,CreateBookRequest createBookRequest) {
        Book book = CreateBookRequest.dtoToModel(createBookRequest);
        book.setId(id);
        boolean exists= bookRepository.existsById(id);
        if(exists){
            bookRepository.save(book);
            try {
                //kafkaBookEventSourcing.updateBookEvent(book);
                kafkaBookEventSourcingOneTopic.sendMessageToSecondPartition(book);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            bookRepository.delete(book.get());
            try {
                //kafkaBookEventSourcing.deleteBookEvent(book.get());
                kafkaBookEventSourcingOneTopic.sendMessageToThirdPartition(book.get());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
