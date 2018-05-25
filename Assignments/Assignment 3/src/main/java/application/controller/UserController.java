package application.controller;

import application.model.User;
import application.service.IUserService;
import application.utils.JSONError;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers() {
        List<User> users = userService.findAll();

        JSONArray jsonArray = new JSONArray();

        for (User user: users) {
            JSONObject userJSON = user.toJSON();
            jsonArray.add(userJSON);
        }

        JSONObject result = new JSONObject();
        result.put("users", jsonArray);

        return new ResponseEntity(result.toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody @Valid User user, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            System.out.println("*******************" + bindingResult);
            throw new ValidationException("Invalid JSON.");
        }
        String password = user.getPassword();
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        User newUser = userService.create(user);
        return new ResponseEntity(newUser.toJSON().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody String jsonString) {
        User user = userService.findById(id);
        if (user == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("User not found.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject userJSON;
        try {
            userJSON = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Bad JSON.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        String username = (String) userJSON.get(User.JSON_KEY_USERNAME);
        if (username != null) {
            user.setUsername(username);
        }

        String password = (String) userJSON.get(User.JSON_KEY_PASSWORD);
        if (password != null) {
            PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }

        String fullname = (String) userJSON.get(User.JSON_KEY_FULLNAME);
        if (fullname != null) {
            user.setFullname(fullname);
        }

        String role = (String) userJSON.get(User.JSON_KEY_ROLE);
        if (role != null) {
            user.setRole(role);
        }

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (constraintViolations.size() > 0) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid user.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        user = userService.update(user);

        return new ResponseEntity(user.toJSON().toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    private ResponseEntity deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("User not found.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        userService.delete(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
