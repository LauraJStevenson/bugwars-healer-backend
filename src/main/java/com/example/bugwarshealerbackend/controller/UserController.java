package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.BaseResponse;
import com.example.bugwarshealerbackend.dto.UserResponse;
import com.example.bugwarshealerbackend.exceptions.ResourceNotFoundException;
import com.example.bugwarshealerbackend.model.User;
import com.example.bugwarshealerbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable(value = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException {
        return userService.updateUser(userId, userDetails);
    }

    @DeleteMapping("/users/{id}")
    public BaseResponse deleteUser(@PathVariable(value = "id") Long userId) {
        return userService.deleteUser(userId);
    }
}