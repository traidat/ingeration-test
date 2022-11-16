package com.example.applicationintegrationtest.integration;

import com.example.applicationintegrationtest.EnvironmentForTest;
import com.example.applicationintegrationtest.model.User;
import com.example.applicationintegrationtest.repository.UserRepository;
import com.example.applicationintegrationtest.service.JedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest extends EnvironmentForTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void createUser_createSuccess_return206() throws Exception {
        User user = new User();
        user.setName("hieutm19");
        user.setUsername("hieutm19");

        mockMvc.perform(post("/user/create")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value("hieutm19"))
                .andExpect(jsonPath("$.name").value("hieutm19"));

        Assertions.assertEquals("hieutm19", jedisService.get("2"));
    }

    @Test
    @Order(2)
    void createUser_userExist_return206() throws Exception {
        User user = new User();
        user.setName("truongvq");
        user.setUsername("truongvq");

        mockMvc.perform(post("/user/create")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(3)
    public void getAllUser_success_return200() throws Exception {
        ScriptUtils.runInitScript(
                new JdbcDatabaseDelegate(postgresql.withDatabaseName("user_db"), ""),
                "add-new-user.sql");

        mockMvc.perform(get("/user/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        Assertions.assertEquals(3, userRepository.findAll().size());
    }

}