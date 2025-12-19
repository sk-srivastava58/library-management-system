package com.library.librarymanagement.service;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //create user
    public User createUser(User user){
        return userRepository.save(user);
    }

    //Get All User
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //Get User By Id
    public User getUserById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found with id: " + id));
    }

    //Deactivate User
    public void deactivateUser(Long id){
        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }
}
