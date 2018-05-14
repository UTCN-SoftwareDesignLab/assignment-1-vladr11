package application.repository;

import application.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> findAllByNameContains(String name);

}
