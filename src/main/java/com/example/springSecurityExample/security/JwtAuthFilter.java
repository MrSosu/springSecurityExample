package com.example.springSecurityExample.security;

import com.example.springSecurityExample.response.ErrorResponse;
import com.example.springSecurityExample.service.TokenBlackListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Getter
    private final List<String> publicEndpoints = List.of(
            "/app/v1/register"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        final String requestUri = request.getRequestURI();

        // verifico che l'endpoint sia pubblico
        if (isEndpointPublic(requestUri)) {
            filterChain.doFilter(request, response);
        }

        // verifico che il tipo di header di autorizzazione sia Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer: ")) {
            sendErrorResponse(response, "NotBearerAuthorizationException",
                    "La richiesta non ha il corretto formato di autorizzazione. Devi usare l'header Bearer token.");
            return;
        }

        jwt = authHeader.substring(7);
        // verifico che il token sia nella blacklist
        if (tokenBlackListService.isPresentToken(jwt)) {
            sendErrorResponse(response, "TokenNotValid", "Il tuo token è invalido o scaduto.");
            return;
        }

        filterChain.doFilter(request, response);

    }

    public boolean isEndpointPublic(String endpoint) {
        return publicEndpoints.stream().anyMatch(publicUrl -> endpoint.matches(publicUrl.replace("**", ".*")));
    }


    public void sendErrorResponse(HttpServletResponse response, String errorType, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .exception(errorType)
                .message(errorMessage)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}
