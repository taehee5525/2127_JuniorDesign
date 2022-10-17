package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Token;
import com.moneytransfer.backendserver.objects.User2;

import java.util.List;
import java.util.Optional;

public interface TokenRepo {

    Token save(Token token);

    Optional<Token> findByToken(String token);

    Optional<Token> findByUserEmail(String email);

    void deleteByUserEmail(String email);

    List<Token> findAll();
}