package com.github.viniciusboos.apirest.services.impl;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.domain.dto.UserDto;
import com.github.viniciusboos.apirest.exceptions.DataIntegrityViolationException;
import com.github.viniciusboos.apirest.exceptions.ObjectNotFoundException;
import com.github.viniciusboos.apirest.repositories.UserRepository;
import com.github.viniciusboos.apirest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário de id " + id + " não existe"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDto userDto) {
        findByEmail(userDto);
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    private void findByEmail(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent() && !user.get().getId().equals(userDto.getId())) throw new DataIntegrityViolationException("User com email " + userDto.getEmail() + " ja registrado");
    }

    @Override
    public User update(UserDto userDto) {
        findByEmail(userDto);
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(findById(id));
    }
}
