package application.authentication;

import application.Constants;
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

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        grantedAuthorities.forEach(x -> authorities.add(x));
        if (authorities.size() > 0) {
            GrantedAuthority authority = authorities.get(0);
            if (authority.getAuthority().equals(Constants.Roles.ADMINISTRATOR)) {
                response.sendRedirect("/admin");
            } else if (authority.getAuthority().equals(Constants.Roles.SECRETARY)) {
                response.sendRedirect("/secretary");
            } else if (authority.getAuthority().equals(Constants.Roles.DOCTOR)) {
                response.sendRedirect("/doctor");
            }
        }
    }
}
