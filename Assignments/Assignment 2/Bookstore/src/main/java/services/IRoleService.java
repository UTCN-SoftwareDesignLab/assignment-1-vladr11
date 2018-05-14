package services;

import model.Role;
import model.User;

import java.util.List;

public interface IRoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

}
