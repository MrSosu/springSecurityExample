package com.example.springSecurityExample.repositories;

import com.example.springSecurityExample.entities.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    Optional<TokenBlackList> findOneByToken(String token);

}
