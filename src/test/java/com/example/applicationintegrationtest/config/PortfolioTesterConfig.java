package com.example.applicationintegrationtest.config;

import com.example.applicationintegrationtest.controller.Portfolio;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortfolioTesterConfig {
    @Bean
    public Portfolio portfolio() {
        return Mockito.spy(new Portfolio());
    }
}
