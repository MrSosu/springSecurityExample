package com.example.springSecurityExample.security;

import com.example.springSecurityExample.request.AuthenticationRequest;
import com.example.springSecurityExample.request.RegisterRequest;
import com.example.springSecurityExample.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").substring(7);
        return new ResponseEntity<>(authenticationService.logout(jwt), HttpStatus.OK);
    }

}
