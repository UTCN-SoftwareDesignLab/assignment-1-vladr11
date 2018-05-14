package services;

import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        Iterable<Role> items = roleRepository.findAll();

        ArrayList<Role> roles = new ArrayList<>();
        items.forEach(roles::add);

        return roles;
    }

    @Override
    public Role findById(Long id) {
        Optional<Role> opt = roleRepository.findById(id);

        Role role = null;
        try {
            role = opt.get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return role;
    }

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        return role;
    }

}
