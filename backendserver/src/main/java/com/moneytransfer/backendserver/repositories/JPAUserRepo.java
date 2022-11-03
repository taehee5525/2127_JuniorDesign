package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JPAUserRepo extends JpaRepository<User2, Long>, UserRepo {

    @Override
    Optional<User2> findByUserEmail(String email);

    @Override
    Optional<User2> findByPhoneNumber(String phoneNumber);

    @Override
    List<User2> findAll();

}
