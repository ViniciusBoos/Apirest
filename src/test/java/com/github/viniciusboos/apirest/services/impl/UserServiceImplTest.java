package com.github.viniciusboos.apirest.services.impl;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.domain.dto.UserDto;
import com.github.viniciusboos.apirest.exceptions.ObjectNotFoundException;
import com.github.viniciusboos.apirest.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Fulano";
    public static final String EMAIL = "fulano@email.com";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDto userDto;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void quandoProcurarPorIdDeveRetornarUmUsuario() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void quandoProcurarPorIdDeveRetornarUmObjectNotFoundException() {
        try {
            userService.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Usuário de id " + ID + " não existe", ex.getMessage());
        }
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL, PASSWORD);
        this.userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
        this.optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}