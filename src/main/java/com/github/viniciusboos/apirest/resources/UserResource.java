package com.github.viniciusboos.apirest.resources;

import com.github.viniciusboos.apirest.domain.User;
import com.github.viniciusboos.apirest.domain.dto.UserDto;
import com.github.viniciusboos.apirest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    public static final String ID = "/{id}";
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = ID)
    public ResponseEntity<UserDto> findByid(@PathVariable Integer id) {
        return ResponseEntity.ok(modelMapper.map(userService.findById(id), UserDto.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(userService.create(userDto).getId()).toUri();
        return ResponseEntity
               .created(uri)
               .build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return ResponseEntity.ok(modelMapper.map(userService.update(userDto), UserDto.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDto> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
