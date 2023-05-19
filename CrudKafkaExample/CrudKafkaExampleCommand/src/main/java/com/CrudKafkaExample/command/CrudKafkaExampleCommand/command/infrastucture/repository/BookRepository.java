package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.infrastucture.repository;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
