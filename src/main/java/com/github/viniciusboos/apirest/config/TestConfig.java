package com.github.viniciusboos.apirest.config;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Fulano", "fulano@email.com", "123");
        User u2 = new User(null, "Ciclano", "ciclano@email.com", "123");
        User u3 = new User(null, "Julano", "Julano@email.com", "123");

        userRepository.saveAll(List.of(u1,u2, u3));
    }
}
