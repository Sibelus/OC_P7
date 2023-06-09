package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.IUserService;
import com.nnk.springboot.service.exceptions.NonExistantUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private IUserService iUserService;
    Logger logger  = LoggerFactory.getLogger(UserController.class);



    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", iUserService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            iUserService.addUser(user);
            model.addAttribute("users", iUserService.getAllUsers());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            User user = iUserService.getUserById(id);
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        } catch (NonExistantUserException e) {
            logger.error("Try to update non-existent user id: " + id + " with URL input");
            return "redirect:/app/error";
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        user.setId(id);
        iUserService.updateUser(user);
        model.addAttribute("users", iUserService.getAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try {
            User user = iUserService.getUserById(id);
            iUserService.deleteUser(user);
            return "redirect:/user/list";
        } catch (NonExistantUserException e) {
            logger.error("Try to delete non-existent user id: " + id + " with URL input");
            return "redirect:/app/error";
        }
    }
}
