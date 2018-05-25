package application.authentication;

import application.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("configureGlobal...");
        auth.userDetailsService(userDetailsService()).passwordEncoder(new Pbkdf2PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure...");
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/js/**", "/vendor/**").permitAll()
                .antMatchers("/patients**", "/consultations**").authenticated()
                .antMatchers("/admin**").hasAuthority(Constants.Roles.ADMINISTRATOR)
                .antMatchers("/secretary**").hasAuthority(Constants.Roles.SECRETARY)
                .antMatchers("/doctor**").hasAuthority(Constants.Roles.DOCTOR)
                .antMatchers("/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .successHandler(new AuthSuccessHandler())
                .failureHandler(new AuthFailureHandler())
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }
}
