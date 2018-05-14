package model;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_KEY_ID, getId());
        jsonObject.put(JSON_KEY_NAME, getName());
        return jsonObject;
    }
}
