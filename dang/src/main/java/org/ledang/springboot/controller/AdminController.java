package org.ledang.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ledang.springboot.dto.UserCreateDTO;
import org.ledang.springboot.dto.UserUpdateDTO;
import org.ledang.springboot.entity.User;
import org.ledang.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    final private UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody UserCreateDTO dto) {
        return userService.addUser(dto);
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDTO dto) {
        return userService.updateUser(userId, dto);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
