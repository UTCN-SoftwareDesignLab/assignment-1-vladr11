package application.service;

import application.Constants;
import application.model.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BootstrapService implements IBootstrapService {

    private UserRepository userRepository;

    public static BootstrapService instance = null;

    @Autowired
    public BootstrapService(UserRepository userRepository) {
        this.userRepository = userRepository;
        instance = this;
    }

    @Override
    public void performBootstrap() {
        createUser();
    }

    private void createUser() {
        User user = new User();
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
        String password = "pass1234";
        String encodedPassword = passwordEncoder.encode(password);

        user.setUsername("vladrusu");
        user.setPassword(encodedPassword);
        user.setFullname("Vlad Rusu");
        user.setRole(Constants.Roles.ADMINISTRATOR);

        userRepository.save(user);
    }
}
