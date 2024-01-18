package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jwt.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LogoutController {
    @GetMapping("/logout")
    public void logout()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = authentication.getCredentials().toString();
        JwtService.addToDenyList(jwtToken);
    }
}
