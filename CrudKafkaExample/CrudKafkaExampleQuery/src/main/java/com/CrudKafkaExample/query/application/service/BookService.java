package com.CrudKafkaExample.query.application.service;

import com.CrudKafkaExample.query.domain.dto.BookListResponse;
import com.CrudKafkaExample.query.infrastructure.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    List<BookListResponse> findAllBooks();

}
