package com.example.bugwarshealerbackend.config;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.jwt.JwtService;
import com.example.bugwarshealerbackend.model.User;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    public AuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ensure that the request has a valid bearer token associated with a logged in user. Promote this user as the authenticated user for the current security context.
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        System.out.println("Authorization header is: " + authorizationHeader);

        if (authorizationHeaderIsInvalid(authorizationHeader)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);
        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
//responsible for extracting authentication information from the "Authorization" header, creating an authentication token,
// and setting it in the security context for further processing in the Spring Security filter chain.
    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        try
        {
            //Check whether the token is deny-listed.
            if(JwtService.isDenyListed(token)) {
                return null;
            }

            String claimUserName = JwtService.getUserNameForToken(token);
            if (claimUserName == null)
            {
                return null;
            }

            User authenticatedUser = userRepository.findByUsername(claimUserName);
            System.out.println("Authenticated user: " + authenticatedUser);
            if (authenticatedUser == null)
            {
                return null;
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            return new UsernamePasswordAuthenticationToken(authenticatedUser.getUsername(), token, authorities);
        }
        catch (MalformedJwtException ex)
        {
            return null;
        }
    }
}
