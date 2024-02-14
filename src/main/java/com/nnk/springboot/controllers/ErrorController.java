package com.nnk.springboot.controllers;

import com.nnk.springboot.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Controller class for the Error folder.
 * Page to display error403.
 */
@Controller
public class ErrorController {
    
    private final UserService userService;
    
    public ErrorController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * To display the 403 error page.
     * Display the principal/s fullname.
     *
     * @param principal the user authenticated.
     * @param model     to parse data to the view.
     * @return the 403.html of the error template folder.
     */
    @GetMapping("/error403")
    public String error(Principal principal, Model model) {
        String fullname = userService.getUserName(principal);
        model.addAttribute("fullname", fullname);
        return "/error/403";
    }
}
