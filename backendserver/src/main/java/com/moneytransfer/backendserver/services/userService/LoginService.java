package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.LoginException;
import com.moneytransfer.backendserver.objects.Token;
import com.moneytransfer.backendserver.objects.User;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.TokenRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LoginService {

    private UserRepo userRepo;
    private TokenRepo tokenRepo;

    public LoginService(UserRepo userRepo, TokenRepo tokenRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    public String login(String userEmail, String password) throws LoginException {

        Optional<User2> queryRet = userRepo.findByUserEmail(userEmail);
        if (queryRet.isEmpty()) {
            throw new LoginException(0);
        }
        User2 user = queryRet.get();
        String salt =  user.getSalt();
        String saltedInput = Util.makeSaltedVal(password, salt);

        if (user.getPassword().equals(saltedInput)) {
            String tokenStr = Util.makeRandomString(32);
            while(tokenRepo.findByToken(tokenStr).isPresent()) {
                //to prevent same token is used.
                tokenStr = Util.makeRandomString(32);
            }
            Token token = new Token();
            token.setToken(tokenStr);
            token.setUserEmail(userEmail);
            token.setTimestamp(System.currentTimeMillis());

            /**
             * Check user already logged in (to prevent two or more user logged in the same account)
             * And if token already exists, then remove it before store the new token.
             */
            Optional<Token> queryRet2 = tokenRepo.findByUserEmail(userEmail);
            if (queryRet2.isPresent()) {
                // if user already in token repo
                Token existToken = queryRet2.get();
                tokenRepo.deleteByUserEmail(existToken.getUserEmail());
            }

            tokenRepo.save(token);
            return tokenStr;

        } else {
            throw new LoginException(1);
        }
    }
}
