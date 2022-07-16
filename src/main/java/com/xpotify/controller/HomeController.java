package com.xpotify.controller;

import com.xpotify.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class HomeController {

    @Secured("ROLE_USER")
    @GetMapping({"/", "/home"})
    public String retrieveFormLoginInfo(Model model,
                                        @AuthenticationPrincipal User user) {
        // In form-based login flow you get UserDetails as principal while in Oauth based flow you get Oauth2User
        log.info("user.getUsername() =>" + user.getUsername());
        log.info("user.getEmail() =>" + user.getEmail());
        log.info("user.getAuthProvider() =>" + user.getAuthProvider());
        log.info("user.getProviderId() =>" + user.getProviderId());

        model.addAttribute("name", user.getName());
        return "home";
    }

    @GetMapping("/test")
    public void test() {
        log.debug("hello");
    }
}
