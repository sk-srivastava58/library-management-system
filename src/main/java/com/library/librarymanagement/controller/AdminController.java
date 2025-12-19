package com.library.librarymanagement.controller;

import com.library.librarymanagement.entity.Role;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;


    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/change-role")
    public String changeRole(@RequestParam String email, @RequestParam Role role){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        user.setRole(role);
        userRepository.save(user);

        return "Role updated to " + role;
    }
}
