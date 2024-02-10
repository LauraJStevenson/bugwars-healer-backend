package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.jwt.JwtService;
import com.example.bugwarshealerbackend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TokenRefreshService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    /**
     * Generates a new refresh token for a given user and updates it in the user repository.
     * This method should be used when a new refresh token needs to be issued to the user.
     *
     * @param user The user object for whom the refresh token is being generated and stored.
     */
    @Transactional
    public void generateAndStoreRefreshToken(User user) {
        String refreshToken = jwtService.createRefreshToken(user.getUsername());
        userRepository.updateRefreshToken(user.getId(), refreshToken);
    }

    /**
     * Validates a given refresh token against a user's stored refresh token.
     * This method is used to verify whether the provided refresh token is valid and belongs to the specified user.
     *
     * @param username The username of the user whose refresh token is to be validated.
     * @param refreshToken The refresh token that needs to be validated.
     * @return true if the refresh token is valid and belongs to the user, false otherwise.
     */
    public boolean validateRefreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getRefreshToken().equals(refreshToken);
    }

    /**
     * Creates a new access token for the given username.
     * This method is typically called after validating a refresh token to issue a new access token.
     *
     * @param username The username for which a new access token is to be created.
     * @return A new JWT access token for the given username.
     */
    public String createNewAccessToken(String username) {
        return jwtService.createToken(username);
    }

    /**
     * Extracts the username from a given refresh token.
     * This method is used to retrieve the username embedded in the refresh token's claims.
     *
     * @param refreshToken The refresh token from which the username is to be extracted.
     * @return The username extracted from the refresh token.
     */
    public String getUsernameFromRefreshToken(String refreshToken) {
        return jwtService.getUserNameForToken(refreshToken);
    }

    /**
     * Rotates the refresh token for a user. It validates the old refresh token, creates a new one,
     * updates it in the user repository, invalidates the old refresh token, and returns a new access token.
     * This method ensures that refresh tokens are rotated regularly for security purposes.
     *
     * @param oldRefreshToken The old refresh token that needs to be rotated.
     * @return A new JWT access token after successfully rotating the refresh token.
     * @throws SecurityException If the old refresh token is invalid.
     */
    @Transactional
    public String rotateRefreshToken(String oldRefreshToken) {
        String username = getUsernameFromRefreshToken(oldRefreshToken);
        if (validateRefreshToken(username, oldRefreshToken)) {
            User user = userRepository.findByUsername(username);

            String newRefreshToken = jwtService.createRefreshToken(username);
            userRepository.updateRefreshToken(user.getId(), newRefreshToken);

            jwtService.invalidateToken(oldRefreshToken);

            return jwtService.createToken(username);
        } else {
            throw new SecurityException("Invalid refresh token");
        }
    }
}
