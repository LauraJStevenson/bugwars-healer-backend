package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.config.SecurityConfig;
import com.example.bugwarshealerbackend.config.TestSecurityConfig;
import com.example.bugwarshealerbackend.dto.BaseResponse;
import com.example.bugwarshealerbackend.dto.UserResponse;
import com.example.bugwarshealerbackend.exceptions.ResourceNotFoundException;
import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.User;

import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static java.lang.Boolean.FALSE;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;


    @Autowired
    private WebApplicationContext webApplicationContext;



    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllUsers() throws Exception {
        // Arrange
        MockitoAnnotations.openMocks(this); // Initialize annotated mocks

        // Mock data and repository method
        User newUser = new User(1, "Testlaura", "Laura", "Stevenson", "12345", "laura@mail.com", "null", null, 0, FALSE);
        List<User> userList = List.of(newUser);

        when(userRepository.findAll()).thenReturn(userList);

        // Act and Assert
        List<User> result = userController.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(userList, result);

    }
    @Test
    void getUserByIdTest() throws ResourceNotFoundException {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();

        // Mocking behavior of userRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        UserResponse responseEntity = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK.toString(), responseEntity.getStatus());
        assertEquals(mockUser, responseEntity.getUser());

        // Verify that userRepository's findById method was called with the correct argument
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    void updateUserTest() throws ResourceNotFoundException {
        // Arrange
        Long userId = 1L;
        User userDetails = new User(1, "Testlaura", "Laura", "Stevenson", "12345", "laura@mail.com", "NULL", null, 0, FALSE);
        User existingUser = new User(2,"TestAshley", "Ashley", "Mical", "45678", "ashley@mail.com", "NULL", null, 0, FALSE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserResponse response = userController.updateUser(userId, userDetails);

        // Assert
        assertEquals(HttpStatus.OK.toString(), response.getStatus());
        User updatedUser = response.getUser();
        assertNotNull(updatedUser);
        assertEquals(userDetails.getUsername(), updatedUser.getUsername());
        assertEquals(userDetails.getFirstname(), updatedUser.getFirstname());
        assertEquals(userDetails.getLastname(), updatedUser.getLastname());
        // Add more assertions based on your specific requirements

        // Verify that the save method was called once
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void createUserTest() throws Exception {
        //Arrange
        User inputUser = new User();
        inputUser.setUsername("testUser");
        inputUser.setPassword("testPassword");

        when(passwordEncoder.encode((anyString()))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn((inputUser));

        //Act
        UserResponse createdUser = userController.createUser(inputUser);

        //Assert
        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUser().getUsername());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUserTest() throws ResourceNotFoundException {
        // Mock data
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Call the method
        BaseResponse response = userController.deleteUser(userId);

        // Verify the result
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }
}