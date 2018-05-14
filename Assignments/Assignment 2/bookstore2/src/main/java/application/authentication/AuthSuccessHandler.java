package application.authentication;

import application.Constants;
import application.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();

        System.out.println("SUCCESS");
        ArrayList grantedAuthoritiesArray = new ArrayList<>();
        grantedAuthorities.forEach(x -> grantedAuthoritiesArray.add(x));
        if (grantedAuthoritiesArray.size() > 0) {
            Role role = (Role) grantedAuthoritiesArray.get(0);
            if (role.getAuthority().equals(Constants.Roles.ADMINISTRATOR)) {
                response.sendRedirect("/admin");
            } else if (role.getAuthority().equals(Constants.Roles.EMPLOYEE)) {
                response.sendRedirect("/");
            }
        }
    }
}
