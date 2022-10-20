package com.moneytransfer.backendserver;

import com.moneytransfer.backendserver.repositories.FriendRepo;
import com.moneytransfer.backendserver.repositories.TokenRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import com.moneytransfer.backendserver.services.userService.AuthService;
import com.moneytransfer.backendserver.services.userService.CreateUserService;
import com.moneytransfer.backendserver.services.userService.FriendService;
import com.moneytransfer.backendserver.services.userService.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigs {

    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final FriendRepo friendRepo;

    public SpringConfigs(UserRepo userRepo, TokenRepo tokenRepo, FriendRepo friendRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.friendRepo = friendRepo;
    }




}
