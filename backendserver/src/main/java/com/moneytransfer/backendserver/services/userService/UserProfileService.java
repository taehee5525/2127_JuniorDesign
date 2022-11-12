package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.exceptions.UserProfileException;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserProfileService {

    private UserRepo userRepo;

    public UserProfileService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean updateProfileImage(String userEmail, String mimetype
            , String original_name, byte[] data) throws UserProfileException {

        if (userRepo.findByUserEmail(userEmail).isEmpty()) {
            throw new UserProfileException(0); //email not exists
        }

        userRepo.updateProfilePhoto(userEmail, mimetype, original_name, data);
        return true;
    }

    public Map<String, String> getUserProfile(String userEmail) throws UserProfileException {
        Map<String, String> ret = new HashMap<>();
        if (userRepo.findByUserEmail(userEmail).isEmpty()) {
            throw new UserProfileException(0); //email not exists
        }
        Optional<User2> userInfoOpt = userRepo.findByUserEmail(userEmail);
        User2 userInfo = userInfoOpt.get();

        ret.put("Content-Type", userInfo.getMimetype());
        ret.put("Content-Length", String.valueOf(userInfo.getByteData().length));

        return ret;
    }

    public byte[] getUserProfileData(String userEmail) throws UserProfileException {
        if (userRepo.findByUserEmail(userEmail).isEmpty()) {
            throw new UserProfileException(0); //email not exists
        }
        Optional<User2> userInfoOpt = userRepo.findByUserEmail(userEmail);
        User2 userInfo = userInfoOpt.get();

        return userInfo.getByteData();
    }
}
