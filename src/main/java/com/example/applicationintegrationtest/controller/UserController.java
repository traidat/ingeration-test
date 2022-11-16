package com.example.applicationintegrationtest.controller;

import com.example.applicationintegrationtest.model.User;
import com.example.applicationintegrationtest.repository.UserRepository;
import com.example.applicationintegrationtest.service.JedisService;
import com.example.applicationintegrationtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final JedisService jedisService;


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.isValidUser(user) && userService.createUser(user) != null) {
            jedisService.set(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(@RequestParam String username) {
        return new ResponseEntity<>(userService.getUser(username).isEmpty(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }
}
