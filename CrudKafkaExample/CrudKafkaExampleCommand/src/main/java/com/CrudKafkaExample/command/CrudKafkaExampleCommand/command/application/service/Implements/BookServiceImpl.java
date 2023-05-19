package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.application.service.Implements;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.dto.CreateBookRequest;
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

    private final KafkaBookEventSourcing kafkaBookEventSourcing;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, KafkaBookEventSourcing kafkaBookEventSourcing){
        this.bookRepository = bookRepository;
        this.kafkaBookEventSourcing = kafkaBookEventSourcing;
    }

    @Override
    public void createBook(CreateBookRequest createBookRequest) {
        Book book = CreateBookRequest.dtoToModel(createBookRequest);
        bookRepository.save(book);
        //Enviando el mensaje
        try {
            kafkaBookEventSourcing.createBookEvent(book);
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
                kafkaBookEventSourcing.updateBookEvent(book);
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
                kafkaBookEventSourcing.deleteBookEvent(book.get());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
