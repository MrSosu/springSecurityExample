package com.example.springSecurityExample.service;

import com.example.springSecurityExample.entities.Utente;
import com.example.springSecurityExample.repositories.UtenteRepository;
import com.example.springSecurityExample.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (utenteOptional.isEmpty()) throw new UsernameNotFoundException("L'utente con email " + email + " non esiste!");
        return utenteOptional.get();
    }

    public List<Utente> getAll() {
        return utenteRepository.findAll();
    }

    public Object getUtenteById(Long id) {
        Optional<Utente> optionalUtente = utenteRepository.findById(id);
        if (optionalUtente.isEmpty()) return new ErrorResponse("UtenteNotFoundException",
                "L'utente con id " + id  + " non esiste!");
        return optionalUtente.get();
    }
}
