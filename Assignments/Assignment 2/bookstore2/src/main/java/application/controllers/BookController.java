package application.controllers;

import application.model.Author;
import application.model.Book;
import application.model.Genre;
import application.reports.ReportFactory;
import application.services.IReportService;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import application.services.IAuthorService;
import application.services.IBookService;
import application.services.IGenreService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private IBookService bookService;
    private IAuthorService authorService;
    private IGenreService genreService;
    private IReportService reportService;

    @Autowired
    public BookController(IBookService bookService, IAuthorService authorService, IGenreService genreService, IReportService reportService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.reportService = reportService;
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooks(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "author", required = false) String author,
                           @RequestParam(value = "genre", required = false) String genre) {
        List<Book> books = new ArrayList<>();
        if (title != null) {
            books = bookService.findAllByTitle(title);
        } else if (author != null) {
            List<Author> authors = authorService.findAllByName(author);
            if (authors.size() > 0) {
                Author _author = authors.get(0);
                books = bookService.findAllByAuthor(_author);
            }
        } else if (genre != null) {
            List<Genre> genres = genreService.findAllByName(genre);
            if (genres.size() > 0) {
                Genre _genre = genres.get(0);
                books = bookService.findAllByGenre(_genre);
            }
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
        book.setPrice(new Double((Long) jsonObject.get(Book.JSON_KEY_PRICE)));

        book = bookService.create(book);
        return new ResponseEntity<>(book.toJSONObject().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/books/{id}/sell", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity selBook(@PathVariable("id") Long id, @RequestBody String jsonString) {
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

        Long quantity = (Long) jsonObject.get(Book.JSON_KEY_QUANTITY);
        System.out.println("json: " + jsonString + "\nquantity: " + quantity);
        if (quantity != null) {
            Long bookQuantity = book.getQuantity();
            Long newQuantity = bookQuantity - quantity;
            book.setQuantity(newQuantity);
            bookService.update(book);
        }

        return new ResponseEntity(HttpStatus.OK);
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

    @RequestMapping(path = "/books/report/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getPDFReport() {
        try {
            String path = reportService.reportOutOfStock(ReportFactory.PDF);
            System.out.println(path);
            File file = new File(path);
            Path filePath = Paths.get(file.getAbsolutePath());

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));

            return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = "/books/report/csv", method = RequestMethod.GET, produces = "text/csv")
    public void getCSVReport(HttpServletResponse response) {
        try {
            String path = reportService.reportOutOfStock(ReportFactory.CSV);
            InputStream inputStream = new FileInputStream(path);

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
