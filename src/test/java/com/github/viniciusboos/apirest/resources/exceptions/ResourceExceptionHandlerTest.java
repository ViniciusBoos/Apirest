package com.github.viniciusboos.apirest.resources.exceptions;

import com.github.viniciusboos.apirest.exceptions.DataIntegrityViolationException;
import com.github.viniciusboos.apirest.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void quandoAcontecerObjectNotFoundExceptionDeveRetornarUmaResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(
                new ObjectNotFoundException("Usuário de id " + 1 + " não existe"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());

        assertEquals("Usuário de id " + 1 + " não existe", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void quandoAcontecerDataIntegrityViolationExceptionDeveRetornarUmaResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolation(
                new DataIntegrityViolationException("User com email fulano@email.com ja registrado"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());

        assertEquals("User com email fulano@email.com ja registrado", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}