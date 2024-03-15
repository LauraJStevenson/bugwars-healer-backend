package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.jwt.JwtService;
import com.example.bugwarshealerbackend.dto.AuthenticatedUser;
import com.example.bugwarshealerbackend.model.User;
import com.example.bugwarshealerbackend.dto.UserLogin;
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

        String jwtToken = JwtService.createToken(userlogin.getUsername());
        return ResponseEntity.ok(new AuthenticatedUser(loggedInUser.getId(), loggedInUser.getUsername(), loggedInUser.getPassword(), loggedInUser.getFirstname(), loggedInUser.getLastname(), loggedInUser.getEmail(), jwtToken));

    }
}
