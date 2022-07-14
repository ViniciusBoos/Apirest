package com.github.viniciusboos.apirest.resources;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.domain.dto.UserDto;
import com.github.viniciusboos.apirest.repositories.UserRepository;
import com.github.viniciusboos.apirest.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Fulano";
    public static final String EMAIL = "fulano@email.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void quandoChamarFindByIdDeveRetornarSucesso() {
        Mockito.when(userService.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDto);

        ResponseEntity<UserDto> response = userResource.findByid(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDto.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void quandoChamarFindAllUsersDeveRetornarSucesso() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDto);

        ResponseEntity<List<UserDto>> response = userResource.findAllUsers();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDto.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void quandoChamarcreateUserDeveRetorarCreated() {
        Mockito.when(userService.create(Mockito.any())).thenReturn(user);

        ResponseEntity<UserDto> response = userResource.createUser(userDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void updateUser() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL, PASSWORD);
        this.userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
    }

}