package application.model;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_TITLE = "title";
    public static final String JSON_KEY_AUTHOR = "author";
    public static final String JSON_KEY_GENRE = "genre";
    public static final String JSON_KEY_QUANTITY = "quantity";
    public static final String JSON_KEY_PRICE = "price";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private Long quantity;
    private Double price;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_KEY_ID, getId());
        jsonObject.put(JSON_KEY_TITLE, getTitle());
        jsonObject.put(JSON_KEY_AUTHOR, getAuthor().toJSONObject());
        jsonObject.put(JSON_KEY_GENRE, getGenre().toJSONObject());
        jsonObject.put(JSON_KEY_PRICE, getPrice());
        jsonObject.put(JSON_KEY_QUANTITY, getQuantity());
        return jsonObject;
    }
}
