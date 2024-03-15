package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.dto.BaseResponse;
import com.example.bugwarshealerbackend.dto.UserResponse;
import com.example.bugwarshealerbackend.exceptions.ResourceNotFoundException;
import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(Long userId) {
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

    public boolean validatePassword(String password) {
        Pattern hasNumber = Pattern.compile("\\d");
        Pattern hasUppercase = Pattern.compile("[A-Z]");

        boolean meetsLengthRequirement = password.length() >= 8;
        boolean containsNumber = hasNumber.matcher(password).find();
        boolean containsUppercase = hasUppercase.matcher(password).find();

        return meetsLengthRequirement && containsNumber && containsUppercase;
    }

    public UserResponse createUser(User user) {

        if (!validatePassword(user.getPassword())) {
            UserResponse userResponse = new UserResponse(null);
            userResponse.setErrorMessage("Password does not meet the requirements.");
            return userResponse;
        }

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

    public UserResponse updateUser(Long userId, User userDetails) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            // If statements are needed to allow partial updates. Will convert to @PatchMapping at later time.

            User currentUser = user.get();


            if (userDetails.getFirstname() != null) {
                currentUser.setFirstname(userDetails.getFirstname());
            }

            if (userDetails.getLastname() != null) {
                currentUser.setLastname(userDetails.getLastname());
            }

            if (userDetails.getPassword() != null) {

                if (!validatePassword(userDetails.getPassword())) {
                    UserResponse userResponse = new UserResponse(null);
                    userResponse.setErrorMessage("Password does not meet the requirements.");
                    return userResponse;
                }

                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                String encryptedPwd = bcrypt.encode(userDetails.getPassword());
                currentUser.setPassword(encryptedPwd);
            }

            if (userDetails.getEmail() != null) {
                currentUser.setEmail(userDetails.getEmail());
            }

            try {
                final User updatedUser = userRepository.save(currentUser);
                return new UserResponse(updatedUser);
            } catch (TransactionSystemException exception) {
                UserResponse userResponse = new UserResponse(null);
                userResponse.setErrorMessage(exception.getMessage());
                userResponse.setStatus(HttpStatus.BAD_REQUEST.toString());

                Throwable cause = exception.getMostSpecificCause();
                if (cause.getClass() == ConstraintViolationException.class) {
                    ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
                    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
                    String errorMsj = "Can not update user do to following violations: ";
                    for (ConstraintViolation<?> violation : violations) {
                        errorMsj += violation.getMessage();
                    }
                    userResponse.setErrorMessage(errorMsj);
                }
                return userResponse;
            }

        } else {
            UserResponse response = new UserResponse(null);
            response.setStatus(HttpStatus.NOT_FOUND.toString());
            response.setErrorMessage("User with ID: " + userId + " does not exist.");
            return response;
        }
    }

    public BaseResponse deleteUser(Long userId) {
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

