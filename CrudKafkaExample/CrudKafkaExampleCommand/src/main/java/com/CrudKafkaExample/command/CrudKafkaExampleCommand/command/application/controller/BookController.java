package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.application.controller;


import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.application.service.BookService;
import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.dto.CreateBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController (BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void book(@RequestBody CreateBookRequest createBookRequest){
        bookService.createBook(createBookRequest);
    }

    @PutMapping("/book/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody CreateBookRequest createBookRequest){
        bookService.updateBook(id, createBookRequest);
    }

    @DeleteMapping ("/book/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }
}
