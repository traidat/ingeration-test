package com.example.applicationintegrationtest.unit;

import com.example.applicationintegrationtest.model.User;
import com.example.applicationintegrationtest.repository.UserRepository;
import com.example.applicationintegrationtest.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class UserUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void checkInvalidUser_validUser_returnTrue() throws Exception {
        User inputUser = new User();
        inputUser.setUsername("truongvq");
        inputUser.setName("Truong");

        Mockito.when(userRepository.findByUsername(inputUser.getUsername())).thenReturn(Optional.empty());

        Assertions.assertTrue(userService.isValidUser(inputUser));
    }

    @Test
    public void checkInvalidUser_userExist_returnFalse() throws Exception {
        User inputUser = new User();
        inputUser.setUsername("truongvq");
        inputUser.setName("Truong");

        Mockito.when(userRepository.findByUsername(inputUser.getUsername())).thenReturn(Optional.of(inputUser));

        Assertions.assertFalse(userService.isValidUser(inputUser));
    }

    @Test
    public void checkInvalidUser_invalidUsername_returnFalse() throws Exception {
        User inputUser = new User();
        inputUser.setUsername("truongvq#%");
        inputUser.setName("Truong");

//        Mockito.when(userRepository.findByUsername(inputUser.getUsername())).thenReturn(Optional.of(inputUser));

        Assertions.assertFalse(userService.isValidUser(inputUser));
    }
}
