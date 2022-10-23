package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Token;
import com.moneytransfer.backendserver.objects.User2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JPATokenRepo extends JpaRepository<Token, Long>, TokenRepo {

    @Override
    Optional<Token> findByToken(String token);

    @Override
    Optional<Token> findByUserEmail(String email);

    @Override
    @Modifying
    @Query("delete from Token t where t.userEmail = ?1")
    void deleteByUserEmail(String email);

    @Override
    @Modifying
    @Query("update Token set timestamp = :currTime where token = :tokenStr")
    void updateTimeStamp(@Param("tokenStr") String token, @Param("currTime") Long timeStamp);
}