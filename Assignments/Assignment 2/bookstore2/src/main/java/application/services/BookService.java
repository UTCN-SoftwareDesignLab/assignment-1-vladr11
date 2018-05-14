package application.services;

import application.model.Author;
import application.model.Book;
import application.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        Iterable<Book> items = bookRepository.findAll();

        ArrayList<Book> books = new ArrayList<>();
        items.forEach(books::add);

        return books;
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> opt = bookRepository.findById(id);

        Book book = null;
        try {
            book = opt.get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return book;
    }

    @Override
    public List<Book> findAllByTitle(String title) {
        Iterable<Book> items = bookRepository.findAllByTitleContains(title);

        ArrayList<Book> books = new ArrayList<>();
        items.forEach(books::add);

        return books;
    }

    @Override
    public List<Book> findAllByAuthor(Author author) {
        Iterable<Book> items = bookRepository.findAllByAuthor(author);

        ArrayList<Book> books = new ArrayList<>();
        items.forEach(books::add);

        return books;
    }

    @Override
    public List<Book> findAllByGenre(Genre genre) {
        Iterable<Book> items = bookRepository.findAllByGenre(genre);

        ArrayList<Book> books = new ArrayList<>();
        items.forEach(books::add);

        return books;
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        if (!bookRepository.existsById(book.getId())) {
            return null;
        }
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        if (!bookRepository.existsById(book.getId())) {
            return false;
        }
        bookRepository.delete(book);
        return true;
    }
}
