package application.services;

import application.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GenreService implements IGenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        Iterable<Genre> items = genreRepository.findAll();

        ArrayList<Genre> genres = new ArrayList<>();
        items.forEach(genres::add);

        return genres;
    }

    @Override
    public Genre findById(Long id) {
        Optional<Genre> opt = genreRepository.findById(id);
        Genre genre = null;

        try {
            genre = opt.get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return genre;
    }

    @Override
    public List<Genre> findAllByName(String name) {
        Iterable<Genre> items = genreRepository.findAllByNameContains(name);

        ArrayList<Genre> genres = new ArrayList<>();
        items.forEach(genres::add);

        return genres;
    }

    @Override
    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public boolean delete(Genre genre) {
        if (!genreRepository.existsById(genre.getId())) {
            return false;
        }
        genreRepository.delete(genre);
        return true;
    }
}
