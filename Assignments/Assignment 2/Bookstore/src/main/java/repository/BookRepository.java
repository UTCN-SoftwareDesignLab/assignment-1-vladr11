package repository;

import model.Author;
import model.Book;
import model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Iterable<Book> findAllByTitleContains(String title);
    Iterable<Book> findAllByAuthor(Author author);
    Iterable<Book> findAllByGenre(Genre genre);

}
