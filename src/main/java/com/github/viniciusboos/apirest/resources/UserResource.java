package com.github.viniciusboos.apirest.resources;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> findByid(@RequestParam Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
