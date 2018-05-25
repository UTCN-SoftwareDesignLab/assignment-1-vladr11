package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageDispatchController {

    @GetMapping(path = "/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping(path = "/secretary")
    public String secretaryPage() {
        return "secretary";
    }

    @GetMapping(path = "/doctor")
    public String doctorPage() {
        return "doctor";
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }
}
