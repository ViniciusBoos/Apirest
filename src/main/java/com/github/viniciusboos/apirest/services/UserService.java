package com.github.viniciusboos.apirest.services;

import com.github.viniciusboos.apirest.domain.User;

import java.util.Optional;

public interface UserService {

    User findById(Integer id);
}
