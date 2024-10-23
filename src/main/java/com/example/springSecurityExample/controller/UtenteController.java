package com.example.springSecurityExample.controller;

import com.example.springSecurityExample.entities.Utente;
import com.example.springSecurityExample.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/v1/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> getUtenteById(@PathVariable Long id) {
        return new ResponseEntity<>(utenteService.getUtenteById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Secured("USER")
    public ResponseEntity<List<Utente>> getAllUtenti() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

}
