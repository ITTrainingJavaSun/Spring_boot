package org.ledang.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ledang.springboot.dto.UserUpdateDTO;
import org.ledang.springboot.entity.User;
import org.ledang.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @GetMapping("/hello")
    public String getUserProfile(Model model) {
        model.addAttribute("mgs", "Thông tin người dùng:");
        return "register";
    }
}
