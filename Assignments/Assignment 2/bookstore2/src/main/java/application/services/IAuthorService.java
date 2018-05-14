package application.services;

import application.model.Author;

import java.util.List;

public interface IAuthorService {

    List<Author> findAll();

    Author findById(Long id);

    List<Author> findAllByName(String name);

    Author create(Author author);

    Author update(Author author);

    boolean delete(Author author);

}
