package application.service;

import application.model.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAllByRole(String role);
    User create(User user);
    User update(User user);
    boolean delete(User user);

}
