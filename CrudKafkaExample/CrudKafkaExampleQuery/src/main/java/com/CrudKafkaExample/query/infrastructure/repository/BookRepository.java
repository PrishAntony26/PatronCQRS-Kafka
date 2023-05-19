package com.CrudKafkaExample.query.infrastructure.repository;

import com.CrudKafkaExample.query.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
}
