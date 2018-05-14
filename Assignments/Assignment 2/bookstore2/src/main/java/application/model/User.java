package application.model;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_USERNAME = "username";
    public static final String JSON_KEY_FULLNAME = "fullname";
    public static final String JSON_KEY_EMAIL = "email";
    public static final String JSON_KEY_ROLE = "role";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String fullname;
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_KEY_ID, getId());
        jsonObject.put(JSON_KEY_USERNAME, getUsername());
        jsonObject.put(JSON_KEY_FULLNAME, getFullname());
        jsonObject.put(JSON_KEY_EMAIL, getEmail());
        jsonObject.put(JSON_KEY_ROLE, getRole().getName());
        return jsonObject;
    }
}
