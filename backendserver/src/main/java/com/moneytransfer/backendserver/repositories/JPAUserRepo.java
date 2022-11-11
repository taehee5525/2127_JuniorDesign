package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Transaction;
import com.moneytransfer.backendserver.objects.User2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JPAUserRepo extends JpaRepository<User2, Long>, UserRepo {

    @Override
    Optional<User2> findByUserEmail(String email);

    @Override
    Optional<User2> findByPhoneNumber(String phoneNumber);

    @Override
    List<User2> findAll();

    @Override
    @Modifying
    @Query(value = "update User2 set mimetype = :mimetype, original_name = :original_name, byte_data = :data"
            + " where user_Email = :userEmail", nativeQuery = true)
    void updateProfilePhoto(String userEmail, String mimetype, String original_name, byte[] data);

}
