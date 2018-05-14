package application.repository;

import application.model.Author;
import application.model.Book;
import application.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Iterable<Book> findAllByTitleContains(String title);
    Iterable<Book> findAllByAuthor(Author author);
    Iterable<Book> findAllByGenre(Genre genre);

}
