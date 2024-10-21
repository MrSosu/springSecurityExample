package com.example.springSecurityExample.security;

import com.example.springSecurityExample.entities.TokenBlackList;
import com.example.springSecurityExample.entities.Utente;
import com.example.springSecurityExample.enums.Role;
import com.example.springSecurityExample.request.AuthenticationRequest;
import com.example.springSecurityExample.request.RegisterRequest;
import com.example.springSecurityExample.response.AuthenticationResponse;
import com.example.springSecurityExample.response.ErrorResponse;
import com.example.springSecurityExample.service.TokenBlackListService;
import com.example.springSecurityExample.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenBlackListService tokenBlackListService;


    public AuthenticationResponse register(RegisterRequest request) {
        Utente utente = Utente.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TO_CONFIRM)
                .build();
        String jwtToken = jwtService.generateToken(utente);
        utenteService.insertUtente(utente);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername().toLowerCase(), request.getPassword()));
        }
        catch (DisabledException e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .exception("DisabledException")
                    .message("L'account è disabilitato")
                    .build(), HttpStatus.FORBIDDEN);
        }
        catch (LockedException e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .exception("LockedException")
                    .message("L'account è locked!")
                    .build(), HttpStatus.FORBIDDEN);
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .exception("BadCredentialsException")
                    .message("Login fallita, credenziali errate!")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        String jwtToken = jwtService.generateToken(utenteService.findOneByEmail(request.getUsername()));
        return new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken), HttpStatus.OK);
    }

    public void logout(String token) {
        tokenBlackListService.insertTokenBlackList(TokenBlackList.builder().token(token).build());
    }

}
