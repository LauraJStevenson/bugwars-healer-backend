package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.model.User;
import com.example.bugwarshealerbackend.service.TokenRefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling token refresh requests.
 */

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TokenRefreshController {
    @Autowired
    private TokenRefreshService tokenRefreshService;

    /**
     * Refreshes the access token using a provided refresh token.
     *
     * @param refreshTokenRequest The request object containing the refresh token.
     * @return ResponseEntity with a new access token if the refresh is successful, or an error response if it fails.
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshedAccessToken = tokenRefreshService.rotateRefreshToken(refreshTokenRequest.getRefreshToken());
            return ResponseEntity.ok(new TokenResponse(refreshedAccessToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Token refresh failed"));
        }
    }
}

/**
 * Request object for refreshing the access token.
 */
class RefreshTokenRequest {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

/**
 * Response object containing a new access token.
 */
class TokenResponse {
    private String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

/**
 * Response object for error messages.
 */
class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
