import application.model.Author;
import application.model.Book;
import application.model.Genre;
import application.repository.BookRepository;
import application.services.BookService;
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
public class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Before
    public void before() {
        bookService = new BookService(bookRepository);

        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        Genre genre3 = new Genre();
        Genre genre4 = new Genre();

        genre1.setId(new Long(1));
        genre1.setName("Roman");
        genre1.setId(new Long(2));
        genre1.setName("Poezie");
        genre1.setId(new Long(3));
        genre1.setName("SciFi");
        genre1.setId(new Long(4));
        genre1.setName("Nuvela");

        Author author = new Author();
        author.setName("Lucian Blaga");

        Book book1 = new Book();
        book1.setId(new Long(1));
        book1.setTitle("Titlu 1");
        book1.setAuthor(author);
        book1.setGenre(genre3);
        book1.setQuantity(new Long(200));
        book1.setPrice(new Double(17.99));

        Book book2 = new Book();
        book2.setId(new Long(2));
        book2.setTitle("Titlu 2");
        book2.setAuthor(author);
        book2.setGenre(genre2);
        book2.setQuantity(new Long(110));
        book2.setPrice(new Double(23.99));

        Book book3 = new Book();
        book3.setId(new Long(3));
        book3.setTitle("Titlu 3");
        book3.setAuthor(author);
        book3.setGenre(genre4);
        book3.setQuantity(new Long(200));
        book3.setPrice(new Double(17.99));

        Book book4 = new Book();
        book4.setId(new Long(4));
        book4.setTitle("Titlu 4");
        book4.setAuthor(author);
        book4.setGenre(genre1);
        book4.setQuantity(new Long(200));
        book4.setPrice(new Double(17.99));


        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Optional<Book> optional = Optional.of(book2);
        Mockito.when(bookRepository.findById(new Long(2))).thenReturn(optional);
        Mockito.when(bookRepository.findAllByTitleContains(new String("Titlu 1"))).thenReturn(books);
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book4);
    }

    @Test
    public void testFindAll() {
        Iterable<Book> books = bookService.findAll();
        ArrayList<Book> booksArrayList = new ArrayList<>();
        books.forEach(booksArrayList::add);
        Assert.assertEquals(booksArrayList.size(), 3);
    }

    @Test
    public void testFindById() {
        Book book = bookService.findById(new Long(2));
        Assert.assertEquals(book.getId(), new Long(2));

    }

    @Test
    public void testFindAllByTitle() {
        Iterable<Book> books = bookService.findAllByTitle("Titlu 1");
        ArrayList<Book> booksArrayList = new ArrayList<>();
        books.forEach(booksArrayList::add);
        Assert.assertEquals(booksArrayList.get(0).getTitle(), new String("Titlu 1"));

    }

    @Test
    public void create() {
        Book book = bookService.create(new Book());
        Assert.assertEquals(book.getId(), new Long(4));
    }

}