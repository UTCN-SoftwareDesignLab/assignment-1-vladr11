package application.services;

import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static UserService instance = null;

    private UserRepository userRepository;

    public static IUserService getInstance() {
        return instance;
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        instance = this;
    }

    @Override
    public List<User> findAll() {
        Iterable<User> items = userRepository.findAll();

        ArrayList<User> users = new ArrayList<>();
        items.forEach(users::add);

        return users;
    }

    @Override
    public User findById(Long id) {
        Optional<User> opt = userRepository.findById(id);

        User user = null;
        try {
            user = opt.get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> opt = userRepository.findByUsername(username);

        User user = null;
        try {
            user = opt.get();
        } catch (NoSuchElementException e) {
            //e.printStackTrace();
            return null;
        }

        return user;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (userRepository.existsById(user.getId())) {
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
        if (!userRepository.existsById(user.getId())) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }
}
