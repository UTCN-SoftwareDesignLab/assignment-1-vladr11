import application.model.Genre;
import application.repository.GenreRepository;
import application.services.GenreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class GenreServiceTest {
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Before
    public void before() {
        genreService = new GenreService(genreRepository);

        Genre genre1 = new Genre();
        genre1.setId(new Long(1));
        genre1.setName("Roman");

        Genre genre2 = new Genre();
        genre2.setId(new Long(2));
        genre2.setName("Drama");

        Genre genre3 = new Genre();
        genre3.setId(new Long(3));
        genre3.setName("Nuvela");

        Genre genre4 = new Genre();
        genre4.setId(new Long(4));
        genre4.setName("Poezie");

        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        genres.add(genre3);

        Mockito.when(genreRepository.findAll()).thenReturn(genres);
        Optional<Genre> optional = Optional.of(genre2);
        Mockito.when(genreRepository.findById(new Long(2))).thenReturn(optional);
        Mockito.when(genreRepository.findAllByNameContains("Roman")).thenReturn(genres);
        Mockito.when(genreRepository.save(Mockito.any())).thenReturn(genre4);
    }

    @Test
    public void testFindAll() {
        Iterable<Genre> genres = genreService.findAll();
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        genres.forEach(genreArrayList::add);
        Assert.assertEquals(genreArrayList.size(), 3);
    }

    @Test
    public void testFindById() {
        Genre genre = genreService.findById(new Long(2));
        Assert.assertEquals(genre.getId(), new Long(2));
    }

    @Test
    public void testFindByName() {
        Genre genre = genreService.findAllByName("Roman").get(0);
        Assert.assertEquals(genre.getName(), "Roman");
    }

    @Test
    public void create() {
        Genre genre = genreService.create(new Genre());
        Assert.assertEquals(genre.getId(), new Long(4));
    }
}

