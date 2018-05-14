package repository;

import model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Iterable<Genre> findAllByNameContains(String name);

}
