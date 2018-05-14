package application.services;

import application.Constants;
import application.model.Right;
import application.model.Role;
import application.model.User;
import application.repository.RightRepository;
import application.repository.RoleRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BootstrapService implements IBootstrapService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RightRepository rightRepository;

    public static BootstrapService instance = null;

    @Autowired
    public BootstrapService(UserRepository userRepository, RoleRepository roleRepository, RightRepository rightRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
        instance = this;
    }

    @Override
    public void performBootstrap() {
        createRights();
        createRoles();
        createUser();
    }

    private void createRights() {
        String[] rightNames = Constants.Rights.RIGHTS;

        for (int i = 0; i < rightNames.length; i++) {
            Right right = new Right();
            right.setName(rightNames[i]);
            rightRepository.save(right);
        }
    }

    private void createRoles() {
        String[] roleNames = Constants.Roles.ROLES;
        Map<String, List<String>> roleRightsMap = Constants.getRolesRights();

        for (int i = 0; i < roleNames.length; i++) {
            Role role = new Role();
            String roleName = roleNames[i];
            role.setName(roleName);

            List<String> rightNames = roleRightsMap.get(roleName);
            ArrayList<Right> rights = new ArrayList<>();
            for (String rightName: rightNames) {
                List<Right> possibleRights = rightRepository.findByName(rightName);
                if (possibleRights.size() > 0) {
                    Right right = possibleRights.get(0);
                    rights.add(right);
                }
            }
            role.setRights(rights);

            roleRepository.save(role);
        }
    }

    private void createUser() {
        User user = new User();
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        String password = "pass1234";
        String encodedPassword = passwordEncoder.encode(password);

        user.setUsername("vladrusu");
        user.setPassword(encodedPassword);
        user.setFullname("Vlad Rusu");
        user.setEmail("vlad.rusu11@gmail.com");

        Role role = roleRepository.findByName("ADMINISTRATOR");
        user.setRole(role);

        userRepository.save(user);
    }
}
