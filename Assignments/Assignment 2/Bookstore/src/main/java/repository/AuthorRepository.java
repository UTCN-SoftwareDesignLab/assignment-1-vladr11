package repository;

import model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> findAllByNameContains(String name);

}
