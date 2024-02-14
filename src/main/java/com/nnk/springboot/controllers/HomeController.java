package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for the home page.
 * Only accessible by ADMIN accounts.
 * Link to back to the login page.
 * Link to manage users.
 */
@Controller
public class HomeController {
    
    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
