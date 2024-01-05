package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.AuthenticatedUser;
import com.example.bugwarshealerbackend.model.User;
import com.example.bugwarshealerbackend.model.UserLogin;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<AuthenticatedUser> login (@Valid @RequestBody UserLogin userlogin) {

        User loggedInUser = userRepository.findByUsername(userlogin.getUsername());
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Salt checkpoint
        boolean passwordsMatch = BCrypt.checkpw(userlogin.getPassword() /*123456*/, loggedInUser.getPassword() /* fhdjskhfd;asjkfsdf62538727732 */);
        if (!passwordsMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // create a token
        UUID randomToken = UUID.randomUUID();
        String token = randomToken.toString();
        // associate token with user
        loggedInUser.setToken(token);
        // save the database
        userRepository.save(loggedInUser);

        return ResponseEntity.ok(new AuthenticatedUser(loggedInUser.getUsername(), loggedInUser.getPassword(), token));

    }
}
