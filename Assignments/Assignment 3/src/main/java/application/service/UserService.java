package application.service;

import application.model.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static UserService instance = null;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        instance = this;
    }

    @Override
    public List<User> findAll() {
        Iterable<User> userIterable = userRepository.findAll();
        ArrayList<User> users = new ArrayList<>();
        userIterable.forEach(users::add);
        return users;
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public List<User> findAllByRole(String role) {
        Iterable<User> userIterable = userRepository.findAllByRole(role);
        ArrayList<User> users = new ArrayList<>();
        userIterable.forEach(users::add);
        return users;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public boolean delete(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public static UserService getInstance() {
        return instance;
    }
}
