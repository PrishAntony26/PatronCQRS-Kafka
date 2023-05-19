package com.CrudKafkaExample.query.application.controller;

import com.CrudKafkaExample.query.application.service.BookService;
import com.CrudKafkaExample.query.domain.dto.BookListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.ACCEPTED);
    }

}
