package com.xpotify.controller;

import com.xpotify.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
@Log4j2
public class DashboardController {

    @GetMapping
    public String test(Model model, @AuthenticationPrincipal User user) {
        // In form-based login flow you get UserDetails as principal while in Oauth based flow you get Oauth2User
        log.info("admin.getUsername() =>" + user.getUsername());

        model.addAttribute("name", user.getName());
        return "home";
    }
}
