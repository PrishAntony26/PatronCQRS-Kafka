package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.application.service;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.dto.CreateBookRequest;

public interface BookService {

    void createBook(CreateBookRequest createBookRequest);

    void updateBook(Long id,CreateBookRequest createBookRequest);

    void deleteBook(Long id);
}
