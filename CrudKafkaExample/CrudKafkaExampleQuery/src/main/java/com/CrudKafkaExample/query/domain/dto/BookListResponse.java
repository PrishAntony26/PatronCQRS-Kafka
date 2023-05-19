package com.CrudKafkaExample.query.domain.dto;

import com.CrudKafkaExample.query.domain.model.Book;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookListResponse {
    private String titulo;

    private String autor;

    private int paginas;

    public static BookListResponse ModelToDto(Book book){
        BookListResponse bookListResponse = new BookListResponse();

        bookListResponse.setAutor(book.getAutor());
        bookListResponse.setTitulo(book.getTitulo());
        bookListResponse.setPaginas(book.getPaginas());

        return bookListResponse;
    }
}
