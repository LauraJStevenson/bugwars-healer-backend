package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.AuthenticatedUserResponse;
import com.example.bugwarshealerbackend.dto.UserLoginRequest;
import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.jwt.JwtService;
import com.example.bugwarshealerbackend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;


import static com.example.bugwarshealerbackend.jwt.JwtService.createToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class LoginControllerTest {

    @Mock
    private UserRepository userRepository;

    private String oldSecret;




    @InjectMocks
    private LoginController loginController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
         oldSecret = JwtService.SECRET_KEY;
        String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
        JwtService.SECRET_KEY = secret;
    }
    @AfterEach
    public void cleanUp (){
        JwtService.SECRET_KEY = oldSecret;
    }

    @Test
    void successfulLogin() {
        String expectedToken = JwtService.createToken("klyndelara");

        // Create a sample user
        UserLoginRequest userLoginRequest = new UserLoginRequest(1L, "klyndelara", "password", "Kimlyn", "DeLara", "kimlyn@mail.com");

        User user = new User();
        user.setUsername("klyndelara");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

        when(userRepository.findByUsername("klyndelara")).thenReturn(user);

//        when(JwtService.createToken("username")).thenReturn("mocked-jwt-token");


        // Perform the login
        AuthenticatedUserResponse authenticatedUserResponse = loginController.login(userLoginRequest);

        // Assert that the response has a 200 status code
        assertNotNull(authenticatedUserResponse);
        assertEquals("klyndelara", authenticatedUserResponse.getUsername());
        assertTrue(BCrypt.checkpw("password",authenticatedUserResponse.getPassword()));
        assertNotEquals(expectedToken, authenticatedUserResponse.getToken());
        //assures that token is different in every login process.




    }


    }
