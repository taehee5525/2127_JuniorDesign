package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User2;

import java.util.List;
import java.util.Optional;

public interface UserRepo {

    User2 save(User2 user2);

    Optional<User2> findByUserEmail(String email);

    Optional<User2> findByPhoneNumber(String phoneNumber);

    List<User2> findAll();

    void updateProfilePhoto(String userEmail, String mimetype, String original_name, byte[] data);
}
