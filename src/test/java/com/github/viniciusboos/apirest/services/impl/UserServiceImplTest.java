package com.github.viniciusboos.apirest.services.impl;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.domain.dto.UserDto;
import com.github.viniciusboos.apirest.exceptions.DataIntegrityViolationException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Fulano";
    public static final String EMAIL = "fulano@email.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

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
    void quandoProcurarPorTodosRetornarUmaListaDeUsuarios() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void quandoProcurarPorTodosDeveRetornarUmaListaVazia() {
        List<User> response = userService.findAll();

        assertEquals(new ArrayList<User>(), response);
    }

    @Test
    void quandoTentarCriarUmUsuarioDeveRetornarUmUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User response = userService.create(userDto);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void quandoTentarCriarUmUsuarioComOMesmoEmailEIDDiferenteDeveRetornarUmaDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            userDto.setId(2);
            userService.create(userDto);
        }catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("User com email " + userDto.getEmail() + " ja registrado", ex.getMessage());
        }
    }

    @Test
    void quandoTentarModificarUmUsuarioDeveRetornarUmUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User response = userService.update(userDto);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void quandoTentarModificarUmUsuarioComOMesmoEmailEIDDiferenteDeveRetornarUmaDataIntegrityViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            userDto.setId(2);
            userService.update(userDto);
        }catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("User com email " + userDto.getEmail() + " ja registrado", ex.getMessage());
        }
    }

    @Test
    void detetarCasoDeSucesso() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        Mockito.doNothing().when(userRepository).delete(Mockito.any());
        userService.delete(ID);
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void deletarCasoEleRetornerObjectNotFoundException() {
        try {
            userService.delete(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Usuário de id " + ID + " não existe", ex.getMessage());
        }
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL, PASSWORD);
        this.userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
        this.optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}