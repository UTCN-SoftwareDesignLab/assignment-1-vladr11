package application.services;

import application.model.Genre;

import java.util.List;

public interface IGenreService {

    List<Genre> findAll();

    Genre findById(Long id);

    List<Genre> findAllByName(String name);

    Genre create(Genre genre);

    boolean delete(Genre genre);

}
