package com.github.viniciusboos.apirest.services;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDto userDto);

    void findByEmail(UserDto userDto);
}
