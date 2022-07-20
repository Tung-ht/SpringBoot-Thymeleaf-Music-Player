package com.xpotify.controller;

import com.xpotify.entity.Song;
import com.xpotify.entity.User;
import com.xpotify.dto.SignUpRequest;
import com.xpotify.service.SongService;
import com.xpotify.service.UserService;
import com.xpotify.utils.AppConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    SongService songService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String viewLoginPage(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "login";
        } else if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))){
            List<Song> songs = songService.getSongsForHomePage();
            model.addAttribute("name", user.getName());
            model.addAttribute("songs", songs);
            return "home";
        } else {
            return "forward:/admin/";
        }
    }

    @GetMapping("/signup")
    public String viewSignupPage(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            model.addAttribute("signUpRequest", new SignUpRequest());
            return "signup";
        } else if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))){
            List<Song> songs = songService.getSongsForHomePage();
            model.addAttribute("name", user.getName());
            model.addAttribute("songs", songs);
            return "home";
        } else {
            return "forward:/admin/";
        }
    }

    @PostMapping("/signup")
    public String saveNewUser(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest,
                              Model model,
                              RedirectAttributes attributes) {
        log.info("Submitted data =>" + signUpRequest);
        if (userService.findByEmail(signUpRequest.getEmail()) != null) {
            model.addAttribute("signUpRequest", signUpRequest);
            model.addAttribute("error", "Email Address already exists!");
            return "signup";
        }
        // save user to database & redirect to login for now
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setAuthProvider(AppConstant.LOCAL_PROVIDER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User savedUser = userService.save(user);
        if (savedUser == null) {
            model.addAttribute("signUpRequest", signUpRequest);
            model.addAttribute("error", "An error occurred while performing your request, please try again!");
            return "signup";
        }
        log.info("new user saved successfully!");
        attributes.addFlashAttribute("success", "Your account created successfully, you can now login with your credentials!");
        return "redirect:/login";
    }
}
