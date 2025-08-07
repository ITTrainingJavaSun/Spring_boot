package org.ledang.springboot.service;

import org.ledang.springboot.dto.UserCreateDTO;
import org.ledang.springboot.dto.UserUpdateDTO;
import org.ledang.springboot.entity.User;

import java.util.List;

public interface UserService {
    User addUser(UserCreateDTO dto);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, UserUpdateDTO dto);
    User getCurrentUser();
    User updateCurrentUser(UserUpdateDTO dto);
    void deleteUser(Long id);
}
