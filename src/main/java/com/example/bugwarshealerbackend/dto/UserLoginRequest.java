package com.example.bugwarshealerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class UserLoginRequest extends BaseResponse{

    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
}
