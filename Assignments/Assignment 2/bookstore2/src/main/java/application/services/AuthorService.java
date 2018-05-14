package application.services;

import application.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        Iterable<Author> items = authorRepository.findAll();

        ArrayList<Author> authors = new ArrayList<>();
        items.forEach(authors::add);

        return authors;
    }

    @Override
    public Author findById(Long id) {
        Optional<Author> opt = authorRepository.findById(id);

        Author author = null;

        try {
            author = opt.get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return author;
    }

    @Override
    public List<Author> findAllByName(String name) {
        Iterable<Author> items = authorRepository.findAllByNameContains(name);

        ArrayList<Author> authors = new ArrayList<>();
        items.forEach(authors::add);

        return authors;
    }

    @Override
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        if (!authorRepository.existsById(author.getId())) {
            return null;
        }
        return authorRepository.save(author);
    }

    @Override
    public boolean delete(Author author) {
        if (!authorRepository.existsById(author.getId())) {
            return false;
        }
        authorRepository.delete(author);
        return true;
    }
}
