package com.example.bugwarshealerbackend.dto;

import com.example.bugwarshealerbackend.model.User;
import lombok.Data;
@Data
public class UserResponse extends BaseResponse{
    private User user;
    public UserResponse(User user) {
        super();
        this.user = user;
    }
}
