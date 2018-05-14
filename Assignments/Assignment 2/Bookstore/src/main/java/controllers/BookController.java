package controllers;

import model.Author;
import model.Book;
import model.Genre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.IAuthorService;
import services.IBookService;
import services.IGenreService;

import java.util.List;

@RestController
public class BookController {

    private IBookService bookService;
    private IAuthorService authorService;
    private IGenreService genreService;

    @Autowired
    public BookController(IBookService bookService, IAuthorService authorService, IGenreService genreService) {
        System.out.println("INITIALIZED*************");
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooks(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "author", required = false) String author,
                           @RequestParam(value = "genre", required = false) String genre) {
        List<Book> books;
        if (title != null) {
            books = bookService.findAllByTitle(title);
        } else if (author != null) {
            Author _author = authorService.findAllByName(author).get(0);
            books = bookService.findAllByAuthor(_author);
        } else if (genre != null) {
            Genre _genre = genreService.findAllByName(genre).get(0);
            books = bookService.findAllByGenre(_genre);
        } else {
            books = bookService.findAll();
        }

        JSONArray jsonBooksArray = new JSONArray();

        for (Book book: books) {
            JSONObject bookObject = book.toJSONObject();
            jsonBooksArray.add(bookObject);
        }

        JSONObject booksObject = new JSONObject();
        booksObject.put("books", jsonBooksArray);

        return booksObject.toJSONString();
    }

    @RequestMapping(path = "/books", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBook(@RequestBody String jsonString) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (jsonObject.get(Book.JSON_KEY_AUTHOR) == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (jsonObject.get(Book.JSON_KEY_GENRE) == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Author author = authorService.findById((Long) jsonObject.get(Book.JSON_KEY_AUTHOR));
        Genre genre = genreService.findById((Long) jsonObject.get(Book.JSON_KEY_GENRE));

        Book book = new Book();

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle((String) jsonObject.get(Book.JSON_KEY_TITLE));
        book.setQuantity((Long) jsonObject.get(Book.JSON_KEY_QUANTITY));
        book.setPrice((Double) jsonObject.get(Book.JSON_KEY_PRICE));

        book = bookService.create(book);
        return new ResponseEntity<>(book.toJSONObject().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/books/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateBook(@PathVariable("id") Long id, @RequestBody String jsonString) {
        Book book = bookService.findById(id);
        if (book == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Long authorId = (Long) jsonObject.get(Book.JSON_KEY_AUTHOR);
        if (authorId != null) {
            Author author = authorService.findById(authorId);
            if (author != null) {
                book.setAuthor(author);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }

        Long genreId = (Long) jsonObject.get(jsonObject.get(Book.JSON_KEY_GENRE));
        if (genreId != null) {
            Genre genre = genreService.findById(genreId);
            if (genre != null) {
                book.setGenre(genre);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        String title = (String) jsonObject.get(Book.JSON_KEY_TITLE);
        if (title != null) {
            book.setTitle(title);
        }

        Long quantity = (Long) jsonObject.get(Book.JSON_KEY_QUANTITY);
        if (quantity != null) {
            book.setQuantity(quantity);
        }

        Double price = (Double) jsonObject.get(Book.JSON_KEY_PRICE);
        if (price != null) {
            book.setPrice(price);
        }

        book = bookService.update(book);

        return new ResponseEntity<>(book.toJSONObject().toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/books/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        bookService.delete(book);
        return new ResponseEntity(HttpStatus.OK);
    }

}
