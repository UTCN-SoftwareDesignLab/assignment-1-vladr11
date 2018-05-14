package application.authentication;

import application.model.User;
import application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import application.services.IUserService;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService == null) {
            userService = UserService.getInstance();
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' does not exist");
        }
        AppUserDetails userDetails = new AppUserDetails(user);
        System.out.println(user.getUsername() + "\n\n\n" + userDetails.getUser().getFullname());
        return userDetails;
    }


}
