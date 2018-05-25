package application.model;

import org.json.simple.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User implements GrantedAuthority {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_USERNAME = "username";
    public static final String JSON_KEY_PASSWORD = "password";
    public static final String JSON_KEY_ROLE = "role";
    public static final String JSON_KEY_FULLNAME = "fullname";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Pattern(regexp = "administrator|secretary|doctor")
    private String role;

    private String fullname;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_ID, getId());
        jsonObject.put(JSON_KEY_USERNAME, getUsername());
        jsonObject.put(JSON_KEY_ROLE, getRole());
        jsonObject.put(JSON_KEY_FULLNAME, getFullname());

        return jsonObject;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        user.setId((Long) jsonObject.get(JSON_KEY_ID));
        user.setUsername((String) jsonObject.get(JSON_KEY_USERNAME));

        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        String encodedPassword = passwordEncoder.encode((String) jsonObject.get(JSON_KEY_PASSWORD));
        user.setPassword(encodedPassword);

        user.setRole((String) jsonObject.get(JSON_KEY_ROLE));
        user.setFullname((String) jsonObject.get((JSON_KEY_FULLNAME)));

        return user;
    }
}
