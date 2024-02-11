package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.BaseResponse;
import com.example.bugwarshealerbackend.dto.UserResponse;
import com.example.bugwarshealerbackend.exceptions.ResourceNotFoundException;
import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable(value = "id") Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return new UserResponse(user.get());
        } else {
            UserResponse response = new UserResponse(null);
            response.setStatus( HttpStatus.NOT_FOUND.toString());
            response.setErrorMessage("User with ID: " + userId + " does not exist.");
            return response;
        }
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable(value = "id") Long userId,
                                           @RequestBody User userDetails) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            // If statements are needed to allow partial updates.
            User currentUser = user.get();

            if (userDetails.getUsername() != null) {
                currentUser.setUsername(userDetails.getUsername());
            }

            if (userDetails.getFirstname() != null) {
                currentUser.setFirstname(userDetails.getFirstname());
            }

            if (userDetails.getLastname() != null) {
                currentUser.setLastname(userDetails.getLastname());
            }

            if (userDetails.getPassword() != null) {
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                String encryptedPwd = bcrypt.encode(userDetails.getPassword());
                currentUser.setPassword(encryptedPwd);
            }

            if (userDetails.getEmail() != null) {
                currentUser.setEmail(userDetails.getEmail());
            }

            try{
                final User updatedUser = userRepository.save(currentUser);
                return new UserResponse(updatedUser);
            } catch (TransactionSystemException exception) {
                UserResponse userResponse = new UserResponse(null);
                userResponse.setErrorMessage(exception.getMessage());
                userResponse.setStatus(HttpStatus.BAD_REQUEST.toString());

                Throwable cause = exception.getMostSpecificCause();
                if(cause.getClass()==ConstraintViolationException.class){
                    ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
                    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
                    String errorMsj = "Can not update user do to following violations: ";
                    for(ConstraintViolation<?> violation : violations) {
                        errorMsj += violation.getMessage();
                    }
                    userResponse.setErrorMessage(errorMsj);
                }
                return userResponse;
            }

        } else {
            UserResponse response = new UserResponse(null);
            response.setStatus( HttpStatus.NOT_FOUND.toString());
            response.setErrorMessage("User with ID: " + userId + " does not exist.");
            return response;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody User user) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPwd = bcrypt.encode(user.getPassword());
        user.setPassword(encryptedPwd);
        try {
            return new UserResponse(userRepository.save(user)) ;
        }catch (DataIntegrityViolationException exception) {
            UserResponse userResponse = new UserResponse(null);
            userResponse.setErrorMessage("unknown error");

            Throwable cause = exception.getMostSpecificCause();
            System.out.println(cause.getClass());
            if(cause.getClass()== PSQLException.class){
                PSQLException psqlException = (PSQLException) cause;
                // Keeping this to improve validation message back to frontend in the future
                //ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
                //if(serverErrorMessage != null) {
                //   System.out.println(serverErrorMessage.getMessage());
                //}

                userResponse.setErrorMessage("Can not create user because: " + psqlException.getMessage());
            }
            return userResponse;
        }
    }

    @DeleteMapping("/users/{id}")
    public BaseResponse deleteUser(@PathVariable(value = "id") Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User currentUser = user.get();
            userRepository.delete(currentUser);
            return new BaseResponse();
        } else {
            BaseResponse response = new BaseResponse();
            response.setStatus( HttpStatus.NOT_FOUND.toString());
            response.setErrorMessage("User with ID: " + userId + " does not exist.");
            return response;
        }
    }
}

