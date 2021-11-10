package com.spark.toy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/main")
    public String mainView() {
        return "index";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/openings")
    public String openingView() {
        return "openings";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/customers")
    public String customerView() {
        return "customers";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public String userView() {
        return "users";
    }
}
