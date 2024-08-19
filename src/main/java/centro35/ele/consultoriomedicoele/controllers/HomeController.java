package centro35.ele.consultoriomedicoele.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Esto buscará src/main/resources/templates/index.html
    }
}
