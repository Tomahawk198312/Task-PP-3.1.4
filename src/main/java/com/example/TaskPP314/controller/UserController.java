package com.example.TaskPP314.controller;

import com.example.TaskPP314.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user")
    public String showInfoYourSelf(Model model) {
        model.addAttribute("user", userService.getAuthUser());
        return "user";
    }

}