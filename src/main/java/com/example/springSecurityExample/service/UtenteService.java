package com.example.springSecurityExample.service;

import com.example.springSecurityExample.entities.Utente;
import com.example.springSecurityExample.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente insertUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public Utente findOneByEmail(String email) {
        Optional<Utente> utenteOptional = utenteRepository.findOneByEmail(email);
        if (utenteOptional.isEmpty()) throw new IllegalArgumentException("L'utente con email " + email + " non esiste!");
        return utenteOptional.get();
    }

}
