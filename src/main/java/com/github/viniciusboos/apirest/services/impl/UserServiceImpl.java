package com.github.viniciusboos.apirest.services.impl;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.exceptions.ObjectNotFoundException;
import com.github.viniciusboos.apirest.repositories.UserRepository;
import com.github.viniciusboos.apirest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário de id " + id + " não existe"));
    }
}
