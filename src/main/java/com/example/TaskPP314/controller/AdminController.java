package com.example.TaskPP314.controller;

import com.example.TaskPP314.model.User;
import com.example.TaskPP314.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String showAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getAuthUser());
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit/{id}")
    public String editUser(@ModelAttribute("usEdit") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}