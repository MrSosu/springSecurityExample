package com.example.springSecurityExample.handler;


import com.example.springSecurityExample.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        if (authException instanceof UsernameNotFoundException) {
            String json = objectMapper.writeValueAsString(new ErrorResponse("UsernameNotFoundException",
                    "Lo username inserito non è valido!!"));
            response.getWriter().write(json);
        }
        else {
            String json = objectMapper.writeValueAsString(new ErrorResponse("LoginFailedException",
                    "Login fallito, riprovare!"));
            response.getWriter().write(json);
        }

    }
}
