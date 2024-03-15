package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.jwt.JwtService;
import com.example.bugwarshealerbackend.dto.AuthenticatedUserResponse;
import com.example.bugwarshealerbackend.model.User;
import com.example.bugwarshealerbackend.dto.UserLoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public AuthenticatedUserResponse login (@Valid @RequestBody UserLoginRequest userlogin) {

        User loggedInUser = userRepository.findByUsername(userlogin.getUsername()); //retrieves a user from the repository based on the provided username in the UserLogin object.
        if (loggedInUser == null) {
            AuthenticatedUserResponse response = new AuthenticatedUserResponse(userlogin.getId(),userlogin.getUsername(),userlogin.getPassword(),userlogin.getFirstname(),userlogin.getLastname(),userlogin.getEmail());
            response.setErrorMessage("User with the specified username '" + userlogin.getUsername() + "' does not exist!");
            response.setStatus(HttpStatus.BAD_REQUEST.toString());
            return response; //checks if the user is not found, it returns a 401 Unauthorized status with an empty response body.
        }

        // Salt checkpoint
        boolean passwordsMatch = BCrypt.checkpw(userlogin.getPassword() /*123456*/, loggedInUser.getPassword() /* fhdjskhfd;asjkfsdf62538727732 */); //Compares the password provided in the UserLogin object with the stored hash pw of the retrieved user.
        if (!passwordsMatch) { //checks if passwords match. If not it returns a 401 Unauthorize status with an empty response body.
            AuthenticatedUserResponse response = new AuthenticatedUserResponse(userlogin.getId(),userlogin.getUsername(),userlogin.getPassword(),userlogin.getFirstname(),userlogin.getLastname(),userlogin.getEmail());
            response.setErrorMessage("Incorrect password for user '" + userlogin.getUsername() + "'!");
            response.setStatus(HttpStatus.BAD_REQUEST.toString());
            return response;
        }

        String jwtToken = JwtService.createToken(userlogin.getUsername()); //Generates a JWT using the JwtService.createToken method containing information of the username
        return new AuthenticatedUserResponse(loggedInUser.getId(), loggedInUser.getUsername(), loggedInUser.getPassword(), loggedInUser.getFirstname(), loggedInUser.getLastname(), loggedInUser.getEmail(), jwtToken);
        //if the login is successful, it returns a 200 status with the AuthenticatedUser object which will contain the information of the authenticated user and token.
    }
}
