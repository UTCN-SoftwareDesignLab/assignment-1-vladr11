package controllers;

import model.Role;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import services.IRoleService;
import services.IUserService;

import java.util.List;

@RestController
public class UserController {

    private IUserService userService;
    private IRoleService roleService;

    @Autowired
    public UserController(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUsers() {
        List<User> users = userService.findAll();

        JSONArray jsonArray = new JSONArray();

        for (User user : users) {
            JSONObject userObject = user.toJSONObject();
            jsonArray.add(userObject);
        }

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("users", jsonArray);

        return responseJSON.toJSONString();
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody String requestJSON) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(requestJSON);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String username = (String) jsonObject.get(User.JSON_KEY_USERNAME);
        if (!validUsername(username)) {
            return new ResponseEntity("{\"message\":\"Invalid username.\"}", HttpStatus.BAD_REQUEST);
        }

        if (usernameTaken(username)) {
            return new ResponseEntity("{\"message\":\"Username already taken.\"}", HttpStatus.BAD_REQUEST);
        }

        String password = (String) jsonObject.get("password");
//        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(password);
        String encodedPassword = "dummy_pass";

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setFullname((String) jsonObject.get(User.JSON_KEY_FULLNAME));
        user.setEmail((String) jsonObject.get(User.JSON_KEY_EMAIL));

        user = userService.create(user);
        return new ResponseEntity(user.toJSONObject().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody String requestJSON) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(requestJSON);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String username = (String) jsonObject.get(User.JSON_KEY_USERNAME);
        if (username != null) {
            if (!validUsername(username)) {
                return new ResponseEntity("{\"message\":\"Invalid username.\"}", HttpStatus.BAD_REQUEST);
            }
            if (usernameTaken(username)) {
                return new ResponseEntity("{\"message\":\"Username taken.\"}", HttpStatus.BAD_REQUEST);
            }
            user.setUsername(username);
        }

        String password = (String) jsonObject.get("password");
        if (password != null) {
            //PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
//            String encodedPassword = passwordEncoder.encode(password);
            String encodedPassword = "dummy_pass";
            user.setPassword(encodedPassword);
        }

        String fullName = (String) jsonObject.get(User.JSON_KEY_FULLNAME);
        if (fullName != null) {
            user.setFullname(fullName);
        }

        String email = (String) jsonObject.get(User.JSON_KEY_EMAIL);
        if (email != null) {
            user.setEmail(email);
        }

        String roleName = (String) jsonObject.get(User.JSON_KEY_ROLE);
        if (roleName != null) {
            Role role = roleService.findByName(roleName);
            if (role == null) {
                return new ResponseEntity("{\"message\":\"Invalid role.\"}", HttpStatus.NOT_FOUND);
            }
            user.setRole(role);
        }

        user = userService.update(user);
        return new ResponseEntity(user.toJSONObject().toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity("{\"message\":\"User not found.\"}", HttpStatus.NOT_FOUND);
        }
        boolean success = userService.delete(user);
        if (!success) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    private boolean usernameTaken(String username) {
        User user = userService.findByUsername(username);
        return (user != null);
    }

    private boolean validUsername(String username) {
        return (username != null && username.length() > 2);
    }
}
