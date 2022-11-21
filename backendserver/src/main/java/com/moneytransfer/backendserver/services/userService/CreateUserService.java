package com.moneytransfer.backendserver.services.userService;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CreateUserService {

    private UserRepo userRepo;
    private AccountLookupService als;

    public CreateUserService(UserRepo userRepo, AccountLookupService als) {
        this.userRepo = userRepo;
        this.als = als;
    }

    public boolean createUser(String email, String name, String password, String phoneNumber) {
        if (userRepo.findByUserEmail(email).isPresent()) {
            return false; // if email already exists, return null
        }

        User2 user = new User2();
        String salt = Util.makeRandomString(16);
        String saltedPw = Util.makeSaltedVal(password, salt);

        user.setUserEmail(email);
        user.setName(name);
        user.setSalt(salt);
        user.setPassword(saltedPw);
        user.setPhoneNumber(phoneNumber);
//        if (phoneNumber != null) {
//            user.setPhoneNumber(phoneNumber);
//        }
        userRepo.save(user);
        als.alsAdd(user.getUserEmail());

        return true;
    }



}
