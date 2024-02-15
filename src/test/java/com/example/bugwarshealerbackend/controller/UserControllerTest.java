package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.BaseResponse;
import com.example.bugwarshealerbackend.dto.UserResponse;
import com.example.bugwarshealerbackend.model.User;
import com.example.bugwarshealerbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllUsersTest() throws Exception {
        User user = new User(1L, "TestUser", "Test", "User", "password", "test@test.com", null, new ArrayList<>(), 0, false);
        List<User> allUsers = Arrays.asList(user);

        given(userService.getAllUsers()).willReturn(allUsers);

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(user.getUsername()));
    }

    @Test
    void getUserByIdTest() throws Exception {
        long userId = 1L;
        UserResponse userResponse = new UserResponse(new User(1L, "TestUser", "Test", "User", "password", "test@test.com", null, new ArrayList<>(), 0, false));

        given(userService.getUserById(userId)).willReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value(userResponse.getUser().getUsername()));
    }

    @Test
    void createUserTest() throws Exception {
        User user = new User(0L, "newUser", "New", "User", "P@ssw0rd", "newuser@test.com", null, new ArrayList<>(), 0, false);
        UserResponse userResponse = new UserResponse(user);

        given(userService.createUser(user)).willReturn(userResponse);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.username").value(user.getUsername()));
    }

    @Test
    void updateUserTest() throws Exception {
        long userId = 1L;
        User userDetails = new User(1L, "updatedUser", "Updated", "User", "NewPassword", "updateduser@test.com", null, new ArrayList<>(), 0, true);
        UserResponse updatedUserResponse = new UserResponse(userDetails);

        given(userService.updateUser(userId, userDetails)).willReturn(updatedUserResponse);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value(userDetails.getUsername()));
    }

    @Test
    void deleteUserTest() throws Exception {
        long userId = 1L;
        BaseResponse baseResponse = new BaseResponse();

        given(userService.deleteUser(userId)).willReturn(baseResponse);

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
