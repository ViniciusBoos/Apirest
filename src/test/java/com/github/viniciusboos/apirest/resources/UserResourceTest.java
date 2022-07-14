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
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Fulano";
    public static final String EMAIL = "fulano@email.com";
    public static final String PASSWORD = "123";

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
    void quandoChamarFindByIdRetornarSucesso() {
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
    void findAllUsers() {
    }

    @Test
    void createUser() {
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