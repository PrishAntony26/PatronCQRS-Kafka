package com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.dto;

import com.CrudKafkaExample.command.CrudKafkaExampleCommand.command.domain.model.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {

    private String titulo;

    private String autor;

    private int paginas;

    public static Book dtoToModel(CreateBookRequest createBookRequest){
        Book book = new Book();
        book.setTitulo(createBookRequest.getTitulo());
        book.setAutor(createBookRequest.getAutor());
        book.setPaginas(createBookRequest.getPaginas());
        return book;
    }

}
