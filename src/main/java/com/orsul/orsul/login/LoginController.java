package com.orsul.orsul.login;

import com.orsul.orsul.orsulUser.OrsulUser;
import com.orsul.orsul.orsulUser.OrsulUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Optional;

public class LoginController {

    @Autowired
    private OrsulUserService orsulUserService;

    @GetMapping("/login")
    public ModelAndView login() {

        ModelAndView login = new ModelAndView("login");
        login.addObject("user",new OrsulUser());
        return login;

    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginForm userLogin) {

        Optional<LoginForm> orsulUser1 = orsulUserService.login(userLogin.getUsername(), userLogin.getPassword());
        System.out.print(orsulUser1);

        if(Objects.nonNull(orsulUser1))
        {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

}
