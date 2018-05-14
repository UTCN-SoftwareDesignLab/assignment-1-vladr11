package database;

import model.Role;
import model.User;

public class Bootstrap {

    public static void main(String[] args) {
        //
    }

    private static void createUser() {
        User user = new User();

        user.setUsername("vladrusu");
        user.setPassword("pass1234");
        user.setEmail("vlad.rusu11@gmail.com");
        user.setFullname("Vlad Rusu");
    }

}
