package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.AuthException;
import com.moneytransfer.backendserver.objects.Token;
import com.moneytransfer.backendserver.repositories.TokenRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private TokenRepo tokenRepo;

    public AuthService(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public String getUserIdFromToken(String tokenStr) throws AuthException {

        Optional<Token> TokenRepoRes = tokenRepo.findByToken(tokenStr);
        if (TokenRepoRes.isEmpty()) {
            throw new AuthException(0); //token does not exist.
        }
        Token token = TokenRepoRes.get();
        if (isExpiredToken(token)) {
            throw new AuthException(1); //token exists but is expired.
        }
        updateTimeStamp(tokenStr);
        return token.getUserEmail();
    }

    private boolean isExpiredToken(Token token) {
        Long timeLimit = Util.TOKEN_EXPIRE_LIMIT;
        Long timePassedFromLastReq = System.currentTimeMillis() - token.getTimestamp();
        return timePassedFromLastReq > timeLimit;
    }

    private void updateTimeStamp(String token) {
        tokenRepo.updateTimeStamp(token, System.currentTimeMillis());
    }
}
