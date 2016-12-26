package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 273cn on 16/12/13.
 */
@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {
    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value="login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
