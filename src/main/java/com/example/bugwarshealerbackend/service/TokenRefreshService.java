package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.jwt.JwtService;
import com.example.bugwarshealerbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRefreshService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public void generateAndStoreRefreshToken(User user) {
        String refreshToken = jwtService.createRefreshToken(user.getUsername());
        userRepository.updateRefreshToken(user.getId(), refreshToken);
    }

    public boolean validateRefreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getRefreshToken().equals(refreshToken);
    }

    public String createNewAccessToken(String username) {
        return jwtService.createToken(username);
    }

    public String getUsernameFromRefreshToken(String refreshToken) {
        return jwtService.getUserNameForToken(refreshToken);
    }

}
