package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.config.SecurityConfig;
import com.example.bugwarshealerbackend.exceptions.ResourceNotFoundException;
import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static java.lang.Boolean.FALSE;
import static java.sql.Types.NULL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
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
        User newUser = new User(1,"TestLaura", "Laura", "Stevenson", "12345", "laura@mail.com", null, 0, false);
        List<User> userList = Arrays.asList(newUser);

        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userController.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(userList, result);

    }
    @Test
    void getUserByIdtest() throws ResourceNotFoundException {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();

        // Mocking behavior of userRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<User> responseEntity = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());

        // Verify that userRepository's findById method was called with the correct argument
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    void updateUsertest() throws ResourceNotFoundException {
        // Arrange
        Long userId = 1L;
        User userDetails = new User(1, "laura", "Laura", "Stevenson", "12345", "laura@mail.com", "NULL", 0, FALSE);;
        User existingUser = new User(1,"TestAshley", "Ashley", "Mical", "45678", "ashley@mail.com", "NULL", 0, FALSE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, userDetails);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        User updatedUser = response.getBody();
        assertNotNull(updatedUser);
        assertEquals(userDetails.getUsername(), updatedUser.getUsername());
        assertEquals(userDetails.getFirstname(), updatedUser.getFirstname());
        assertEquals(userDetails.getLastname(), updatedUser.getLastname());
        // Add more assertions based on your specific requirements

        // Verify that the save method was called once
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void createUsertest() throws Exception {
        //Arrange
        User inputUser = new User();
        inputUser.setUsername("testUser");
        inputUser.setPassword("testPassword");

        when(passwordEncoder.encode((anyString()))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn((inputUser));

        //Act
        User createdUser = userController.createUser(inputUser);

        //Assert
        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());

        verify(userRepository, times(1)).save(any(User.class));


    }

    @Test
    void deleteUsertest() throws ResourceNotFoundException {
        // Mock data
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Call the method
        Map<String, Boolean> response = userController.deleteUser(userId);

        // Verify the result
        assertTrue(response.get("deleted"));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }
}