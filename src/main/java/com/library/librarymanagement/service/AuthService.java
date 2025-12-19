package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.LoginRequest;
import com.library.librarymanagement.dto.RegisterRequest;
import com.library.librarymanagement.entity.Role;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.exception.BadRequestException;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MEMBER);
        user.setEnabled(true);

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("User not Found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Invalid Password");
        }

        return jwtUtil.generateToken(user.getEmail(),user.getRole().name());

    }
}
