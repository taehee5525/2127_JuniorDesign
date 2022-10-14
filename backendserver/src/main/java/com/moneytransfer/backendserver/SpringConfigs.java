package com.moneytransfer.backendserver;

import com.moneytransfer.backendserver.repositories.UserRepo;
import com.moneytransfer.backendserver.services.userService.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigs {

    private final UserRepo userRepo;

    public SpringConfigs(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public CreateUserService createUserService() {
        return new CreateUserService(userRepo);
    }

}
