package com.example.springSecurityExample.service;

import com.example.springSecurityExample.entities.TokenBlackList;
import com.example.springSecurityExample.repositories.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenBlackListService {

    @Autowired
    private TokenBlackListRepository repository;

    public TokenBlackList insertTokenBlackList(TokenBlackList tokenBlackList) {
        return repository.save(tokenBlackList);
    }

    public boolean isPresentToken(String token) {
        return repository.findOneByToken(token).isPresent();
    }

}
