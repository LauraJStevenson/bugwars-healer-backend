package com.example.bugwarshealerbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLogin {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
}
