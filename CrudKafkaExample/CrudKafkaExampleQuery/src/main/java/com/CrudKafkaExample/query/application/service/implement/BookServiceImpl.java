package com.CrudKafkaExample.query.application.service.implement;

import com.CrudKafkaExample.query.application.service.BookService;
import com.CrudKafkaExample.query.domain.dto.BookListResponse;
import com.CrudKafkaExample.query.domain.model.Book;
import com.CrudKafkaExample.query.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookListResponse> findAllBooks() {
        List<Book> books= bookRepository.findAll();
        List<BookListResponse> bookListResponses = new ArrayList<>();
        books.stream().forEach(book -> bookListResponses.add(BookListResponse.ModelToDto(book)));
        return bookListResponses;
    }
}
