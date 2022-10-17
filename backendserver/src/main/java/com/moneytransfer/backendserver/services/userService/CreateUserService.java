package com.moneytransfer.backendserver.services.userService;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.springframework.stereotype.Service;

public class CreateUserService {

    private UserRepo userRepo;

    public CreateUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean createUser(String email, String password, String phoneNumber) {
        if (userRepo.findByUserEmail(email).isPresent()) {
            return false; // if email already exists, return null
        }

        User2 user = new User2();
        String salt = Util.makeRandomString(16);
        String saltedPw = Util.makeSaltedVal(password, salt);

        user.setUserEmail(email);
        user.setSalt(salt);
        user.setPassword(saltedPw);
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        userRepo.save(user);

        return true;
    }


}