package com.example.lab01.security;

import com.example.lab01.service.domain.UserDomainService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDomainService userDomainService;

    public JwtFilter(JwtHelper jwtHelper, UserDomainService userDomainService) {
        this.jwtHelper = jwtHelper;
        this.userDomainService = userDomainService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") ||
               path.startsWith("/h2-console") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/swagger-resources") ||
               path.startsWith("/configuration") ||
               path.startsWith("/webjars");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(JwtConstants.HEADER_STRING);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            jwt = authorizationHeader.substring(JwtConstants.TOKEN_PREFIX.length());
            try {
                username = jwtHelper.extractUsername(jwt);
            } catch (Exception e) {
                // Invalid token
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDomainService.loadUserByUsername(username);

            if (jwtHelper.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
