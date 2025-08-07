package org.ledang.springboot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ledang.springboot.dto.UserCreateDTO;
import org.ledang.springboot.dto.UserUpdateDTO;
import org.ledang.springboot.entity.User;
import org.ledang.springboot.enums.Role;
import org.ledang.springboot.repository.UserRepository;
import org.ledang.springboot.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User addUser(UserCreateDTO dto) {
        log.info("Bắt đầu thêm người dùng mới: {}", dto.getUsername());

        User userToAdd = new User();
        userToAdd.setUsername(dto.getUsername());
        userToAdd.setPassword(passwordEncoder.encode(dto.getPassword()));
        userToAdd.setEmail(dto.getEmail());
        userToAdd.setFullName(dto.getFullName());
        userToAdd.setPhone(dto.getPhone());
        userToAdd.setRole(Role.USER);

        User savedUser = userRepository.save(userToAdd);
        log.info("Đã thêm user thành công với id: {}", savedUser.getId());
        return savedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> getAllUsers() {
        log.info("Lấy danh sách tất cả người dùng");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("Tìm người dùng với id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Không tìm thấy user với id: {}", id);
                    return new RuntimeException("User not found!");
                });
    }

    @Override
    public User updateUser(Long id, UserUpdateDTO dto) {
        log.info("Cập nhật thông tin user id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Không tìm thấy user với id: {}", id);
                    return new RuntimeException("User not found!");
                });

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());

        User updatedUser = userRepository.save(user);
        log.info("Đã cập nhật user id: {}", id);
        return updatedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteUser(Long id) {
        log.info("Xoá user với id: {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Không tìm thấy user để xoá với id: {}", id);
            throw new RuntimeException("User not found!");
        }
        userRepository.deleteById(id);
        log.info("Xoá user id: {} thành công", id);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Lấy thông tin user đang đăng nhập: {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Không tìm thấy user đang đăng nhập: {}", username);
                    return new RuntimeException("Current user not found!");
                });
    }

    @Override
    public User updateCurrentUser(UserUpdateDTO dto) {
        User currentUser = getCurrentUser();
        log.info("Cập nhật thông tin của user đang đăng nhập: {}", currentUser.getUsername());

        currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        currentUser.setFullName(dto.getFullName());
        currentUser.setPhone(dto.getPhone());
        currentUser.setEmail(dto.getEmail());

        User updated = userRepository.save(currentUser);
        log.info("Đã cập nhật user hiện tại thành công với id: {}", updated.getId());
        return updated;
    }
}
