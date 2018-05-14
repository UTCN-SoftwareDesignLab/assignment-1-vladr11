package application.services;

import application.model.Role;

import java.util.List;

public interface IRoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

}
